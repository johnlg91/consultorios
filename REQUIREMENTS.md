# Consultorios — Requirements & Planning

**Status:** v0.2 — architecture and domain model settled with the owner on 2026-07-24. Phase 1
(schema rewrite) is next; this doc will keep evolving as we go. See
[PROJECT_REVIEW.md](PROJECT_REVIEW.md) for the original codebase assessment this plan builds on.

## 1. Purpose

Manage the rental of medical offices ("consultorios") to professionals: track offices, tenants,
who's renting what and when, payments (with evidence photos), building expenses, and a weekly
occupancy schedule with room for one-off extra bookings.

## 2. Decisions locked in

- **Backend**: keep and modernize (`consultorios-back`). **Frontend**: rebuild from scratch
  (`consultorios-front`) — the old one reused only as a spec (domain shapes, validation rules,
  the Vacancias matrix interaction design), not as code.
- **Platform**: a **local web app** — Spring Boot backend + React frontend, both running on the
  owner's own machine, opened via browser at `localhost`. No hosting cost/setup now; the same
  stack can move to a small server later for remote access or a WhatsApp bot webhook, with no
  rewrite required either way.
- **Database**: MySQL → **H2**, file-based (persists across restarts; in-memory only for tests).
- **Persistence layer**: stay on **Spring Data JDBC** (not JPA/Hibernate, not jOOQ) — already in
  place, and it sidesteps the classic "ORM fights the migration tool over schema ownership"
  problem since Spring Data JDBC never auto-generates DDL. A move to a different query layer
  (JPA, jOOQ, or GraphQL at the API level) is an explicit maybe-later idea, not scoped now.
- **Schema migrations**: introduce **Flyway**, replacing the old test-only SQL fixture as the
  source of truth for the schema.
- **No data migration needed** — confirmed fresh start, no real production data exists anywhere.
- **Rewrite in English**, incrementally, confirming with the owner before each implementation
  step — starting with the database/schema.
- **No users/auth/login at all**: the old multi-user login system (`Usuario` and everything wired
  to it — Spring Security, the two Thymeleaf login/user pages) is deleted entirely. Single
  implicit admin, no roles. This was originally required by a school project rubric, not by the
  real use case — auth can be reintroduced later if this ever goes remote.
- **Framework upgrade**: Spring Boot 2.7 → **3.5.x**, Java 18 → **21 LTS**, Gradle wrapper bumped
  into the 8.12+ range, `javax.*` → `jakarta.*` (mail + servlet code — a small, isolated 5-file
  change, see §6), MySQL connector removed.
- **Office photos**: not needed. **Payment evidence photos** (proof of a money transfer): needed —
  stored as files on local disk with a path reference in the DB, not a blob.
- **Notifications**: stay email-only for now. A WhatsApp bot is a real future idea, but explicitly
  deferred — not designing an abstraction for it prematurely.
- **Frontend stack**: React + Vite + TypeScript — not Create React App, which (not React itself)
  was the likely source of the old app's slowness. Adding real routing (`react-router`, absent
  before), `@tanstack/react-query` for data fetching (loading/error states for free — the old app
  had none), React Hook Form + Zod (modern equivalent of the old Formik+Yup), and Tailwind CSS +
  a lightweight component set instead of MUI, since load speed was an explicit goal and MUI was
  the heaviest part of the old dependency tree.

## 3. The core model change: from manual contracts to tracked tenant activity

The old app required manually filling out a "Contract" form (dates, type, rate) to start renting
an office to a tenant. The new flow removes that step:

**Add a tenant → start a tenant activity for them at a specific office (no contract form, no
end date) → it tracks automatically → ending it (or disabling the tenant) closes it.**

A tenant can hold **multiple concurrent tenant activities** against different offices (e.g. office
2 on Monday evenings and office 3 on Friday mornings, at the same time) — confirmed real scenario,
not an edge case to special-case away.

### Billing: fixed monthly rate, with flexible extra hours

Renting has historically been in "módulos" — a módulo is 4 hours/week, at a fixed monthly rate
(sidesteps the fact that months don't have a consistent number of weeks). Real complications the
new model needs to handle:
- Some tenants rent fractional módulos (e.g. 6 hrs/week = 1.5 módulos) — solved by storing the
  recurring weekly commitment directly in hours, not as an integer módulo count.
- Tenants sometimes rent **extra, one-off hours** in a given week, opportunistically, only when
  the office happens to be free — this is genuinely new: a booking tied to a specific calendar
  date (not a repeating weekday), which must be validated against real occupancy, must show up on
  the vacancy matrix for that specific week, must count in occupancy reports, and must add an
  extra charge to that month's bill.
- The base monthly rate stays an explicit, fixed, stored number per tenant activity (the system
  can suggest a default of `hours/week ÷ 4 × office's módulo price`, but it's always overridable —
  rent is ultimately whatever's agreed with the tenant).

## 4. Target domain model (English names, H2)

| Entity | Key fields | Notes |
|---|---|---|
| **Office** (was Consultorio) | number, monthly_module_price, equipment, active | No image field. Dropped `area_size`/`specialties` from the old schema — not needed going forward. |
| **Tenant** (was Profesional) | national_id (unique), first_name, last_name, nickname, specialty, subscription_date, address, mobile_phone, email, notes, active | Disabling cascades to close any open TenantActivities (an application-layer transaction, not a DB cascade, since tenants are soft-deleted not hard-deleted). |
| **TenantActivity** (NEW — replaces ContratoDeAlquiler; no manual "create contract" step) | tenant_id, office_id, start_date, end_date (nullable = still open), monthly_rate, notes | A tenant can have several concurrent open ones against *different* offices. Each ends independently, or all open ones close when the Tenant is disabled. Drops the old `tipoDeAlquiler` (NORMAL/EXCEPCIONAL) distinction — verified it was never actually used in billing, only in two dead-end filter endpoints. |
| **Vacancy** (was AlquilerVacancia) | tenant_activity_id, day_of_week, start_time, end_time | The normal recurring weekly pattern; several rows per tenant activity is normal (e.g. Mon 8-12 + Wed 8-12). Hours derive from start/end time, so fractional módulos fall out naturally. |
| **ExtraHours** (NEW) | tenant_activity_id, date (a specific day, not a weekday), start_time, end_time, rate_charged, active | One-off hours beyond the normal Vacancy pattern. Defaults `rate_charged` to `office.monthly_module_price ÷ 4`, overridable. Validated against conflicts; reflected in the vacancy matrix, occupancy reports, and that month's bill. |
| **Payment** (was TransaccionDeAlquiler) | tenant_activity_id, transaction_date, type, payment_method, amount (BigDecimal), evidence_image_path (NEW, nullable), active | Evidence photos stored on disk (e.g. `data/payment-evidence/`), only the path kept in the DB. |
| **Expense** (was Expensa) | description, expense_date, amount (BigDecimal), recurrence, payment_date, active | Renamed only, not restructured. |

**Money/date types**: `BigDecimal` for all money fields (the old `TransaccionDeAlquiler.cantidad`
was a `double` despite a `decimal(12,2)` column — a real rounding-error bug being fixed by
construction here), `LocalDate`/`LocalTime` instead of `java.sql.Date`/`Time`.

## 5. Open questions

Still open, not blocking Phase 1:

- [ ] **Billing proration**: when a TenantActivity starts/ends mid-month, is that month
      full-charged, daily-prorated, or deferred to the next full month? Calendar-month billing, or
      a cycle anchored to the TenantActivity's own start day-of-month? (Needed before the billing
      service is built — Phase 2.)
- [ ] **Manual adjustment payments**: `Payment.type` keeps the old credit/debit idea, but the
      balance will be computed dynamically from `monthly_rate` + `ExtraHours`. Can a one-off
      manual charge/adjustment still be entered as a `Payment` row without double-counting an
      already-accrued month?
- [ ] **Office disable cascade**: should disabling an *Office* also cascade-close open
      TenantActivities against it, the way disabling a *Tenant* does?
- [ ] **Reportes (reports)**: keep as tables, or worth adding the charts that chart.js was
      installed for in the old app but never actually used?
- [ ] **Mobile/responsive use**: was this ever used on a phone/tablet (e.g. checking the schedule
      on the go), or is desktop-only fine? Affects Phase 5 (frontend) layout decisions.

## 6. Technical notes for implementation

- **Dependencies to add**: `org.flywaydb:flyway-core` (H2 support bundled since Flyway 10),
  `runtimeOnly 'com.h2database:h2'`. **To remove**: `mysql:mysql-connector-java`,
  `spring-boot-starter-security`, `spring-boot-starter-thymeleaf`, `spring-security-test`.
- **`jakarta.*` migration scope is exactly 5 files**: `email/EmailServiceImpl.java`
  (`javax.mail.*`), and 4 files in `logging/` (`javax.servlet.*`): `LoggingInterceptor.java`,
  `RequestAdapter.java`, `LoggingServiceImpl.java`, `LoggingService.java`. Leave
  `org.springframework.boot.web.servlet.DispatcherType` imports alone — Spring's own namespaced
  type, unaffected by the jakarta split. No model/repository/controller code touches javax/jakarta
  directly, so the framework bump needs zero changes there.
- **H2/Flyway config**: `jdbc:h2:file:./data/consultorios-db;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE`
  for the real app; same flags on `jdbc:h2:mem:consultorios-test;DB_CLOSE_DELAY=-1` for tests (the
  `DB_CLOSE_DELAY=-1` matters — without it an in-memory H2 DB can vanish between pooled-connection
  closes mid-test-run). `./data/` gitignored, same convention later for `data/payment-evidence/`.
  **Gotcha found the hard way**: Spring Data JDBC always quotes generated identifiers, lowercase
  (e.g. `INSERT INTO "office"`) — but H2 upper-cases *unquoted* DDL by default, so a plain
  `create table office (...)` migration actually creates `OFFICE`, and every quoted-lowercase
  query then fails with "table not found". `DATABASE_TO_LOWER=TRUE` (+ the case-insensitive flag
  as a safety net) fixes it once, in the URL, rather than needing every migration to quote every
  identifier by hand.
- **Dialect translation** (MySQL → H2): `int auto_increment` → `BIGINT GENERATED BY DEFAULT AS
  IDENTITY`; `enum(...)` columns → plain `VARCHAR` + a Java enum; `tinyint(1)` → native `BOOLEAN`.
- **Conflict validation** (Vacancy/ExtraHours overlap checks): pure interval-overlap math on date
  ranges + time ranges, no need to enumerate calendar dates. Store `day_of_week` as
  `java.time.DayOfWeek`'s name directly and compute an `ExtraHours.date`'s day-of-week in Java
  before querying, to sidestep MySQL-vs-H2-vs-ISO day-numbering mismatches. Keep `TenantActivity`,
  `Vacancy`, and `ExtraHours` as three independent Spring Data JDBC aggregate roots (not
  nested/shared) — nesting would make saving a TenantActivity delete-and-reinsert all its Vacancy
  rows (Spring Data JDBC's whole-aggregate-replace semantics), breaking independent per-slot edits.
- **Testing**: H2 makes fast, real automated tests practical for the first time (in-memory H2 for
  tests instead of a hand-seeded live MySQL instance) — prioritize coverage for conflict
  validation, TenantActivity lifecycle (open/close/cascade), and billing calculations.

## 7. Known backend bugs to fix during the rewrite

Not to be carried forward silently:

1. `TransaccionesDeAlquilerRepositorio.getTransaccionDeAlquiler()` queries the wrong table
   (`CONSULTORIOS` instead of the payments table).
2. `TransaccionesDeAlquilerRepositorio.deleteTransaccionDeAlquiler()` soft-deletes the wrong table.
3. `ContratosDeAlquilerRepositorio.getContratosPorNumeroDeConsultorio()` joins on the wrong column,
   returning incorrect tenant names.
4. `ContratosDeAlquilerRepositorio.getContratosConNombres()` references a `MONTO_A_PAGAR` column
   that doesn't exist in the schema.
5. `AlquilerVacancia` creation has **zero** overlap validation today (only ever checked client-side
   in the old frontend, trivially bypassable) — the new `Vacancy`/`ExtraHours` conflict validation
   fixes this for real, server-side.
6. The vacancy/occupancy report sums a recurring slot's hours once per matched row without
   multiplying by how many times that weekday actually occurs in the requested date range (so a
   1-week report and a 1-year report return the same number for the same weekly slot).
7. Payments have no working delete endpoint (the old handler was dead code calling a nonexistent
   method).
8. Money amounts were stored as Java `double` despite `decimal(12,2)` columns — fixed by using
   `BigDecimal` in the new entities (see §4/§6).

## 8. Phased roadmap

### Phase 1 — Schema + core entity rewrite (next; each step confirmed with the owner before implementing)

| Step | Contents |
|---|---|
| 1.0 | Add H2 + Flyway to `build.gradle`; H2 file-mode datasource config; drop the now-meaningless `PropertyTest.java` |
| 1.1 | `V1__create_office_and_tenant.sql` |
| 1.2 | `Office`/`Tenant` records + repos + controllers; delete `Consultorio`/`Profesional` + their old repos/controllers/tests/SQL fixtures |
| 1.3 | `V2__create_tenant_activity.sql` |
| 1.4 | `TenantActivity` record/repo/controller incl. tenant-disable cascade + duplicate-activity guard; delete `ContratoDeAlquiler` + repo/controller |
| 1.5 | `V3__create_vacancy_and_extra_hours.sql` |
| 1.6 | `Vacancy`/`ExtraHours` records/repos + conflict validation service; delete `AlquilerVacancia` + repo/controller (highest-complexity step — may split into recurring-only then extra-hours-only) |
| 1.7 | `V4__create_payment_and_expense.sql` + records/repos/controllers, fixing bugs #1-4/#7 above, adding evidence upload/retrieval; delete `TransaccionDeAlquiler`/`Expensa` + repos/controllers |
| 1.8 | Billing/debt calculation service — deferred until the proration open questions (§5) are answered |
| 1.9 | Test cleanup: H2 in-memory test config; new tests for conflict validation + billing |

The Spring Boot 3.5/Java 21/Gradle/jakarta framework bump is a separate, largely mechanical track
(touches only the 5 files in §6 plus `build.gradle`/the wrapper) — planned for **after** Phase 1
lands, so the app stays in a working state after every step instead of having schema work and a
major framework bump both in flight at once.

### Phase 2 — Billing engine, extra hours, vacancy matrix & reports
Debt/balance service, updated vacancy matrix query (Vacancy + ExtraHours combined for a given
week), fixed occupancy report math (bug #6 above).

### Phase 3 — Payment evidence upload
File-storage service, validation (type/size), retrieval — the column/endpoints are scaffolded in
step 1.7, this phase is the real implementation once usage patterns are clearer.

### Phase 4 — Testing
Real coverage now that H2 makes it practical — conflict validation, TenantActivity lifecycle,
billing calculations, reports.

### Phase 5 — Frontend rebuild
React + Vite + TypeScript + `react-router` + `@tanstack/react-query` + React Hook Form + Zod +
Tailwind. Rebuild each screen against the new API — no login screen needed. Reuse the old app's
Yup validation rules and Vacancias matrix interaction design as reference, not as code. Daily use:
the built frontend copies into Spring Boot's static resources so one jar serves both API and UI on
one port; dev: Vite's own dev-server proxy replaces the old hand-rolled `ReactAppProxy` (deleted).

### Phase 6 — Deferred, not designed yet
Re-adding auth if ever hosted remotely; the WhatsApp bot notification channel; a possible future
query-language change (JPA/jOOQ/GraphQL) — flagged by the owner as a maybe-later idea, meaning
still to be clarified when it becomes relevant.

## 9. Process note

Confirm with the owner before implementing each concrete step above, starting with 1.0/1.1. This
doc gets updated as decisions are made along the way — it's the living record; a plan-mode
implementation plan was also produced during the 2026-07-24 session but this doc is the durable
source of truth going forward.
