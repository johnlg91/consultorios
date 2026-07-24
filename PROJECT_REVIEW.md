# Consultorios — Project Review

**Date:** 2026-07-23
**Scope:** `consultorios-back` (Spring Boot / MySQL) and `consultorios-front` (React/TypeScript)
**Purpose:** Assess what's salvageable before reviving and improving the project.

## Context

This is a small solo project for managing the rental of medical offices ("consultorios") to
professionals/tenants — contracts, payments, expenses, and a weekly occupancy schedule
("vacancias"). Both halves live in one repo and were built between **2023-03-01 and
2023-12-17**, then untouched (one stray file-rename commit in 2024-06-18). Nothing has run in
~2.5 years, no CI, no docker-compose, no deployment docs.

---

## TL;DR

- **Backend domain model and API surface are the strongest asset.** The entity design (Offices,
  Professionals, Contracts, Payments, Expenses, Vacancy schedule, Users) is coherent and covers
  the real business. Worth keeping and evolving rather than redesigning from scratch.
- **Backend has real bugs and is currently wide open** (security filter chain is
  `permitAll()` on every endpoint) and has **live secrets committed to source control**
  (DB password, Gmail SMTP app password). Both need fixing before this touches a real network.
- **Backend framework is end-of-life**: Spring Boot 2.7 (EOL), Java 18 (non-LTS, long dead).
  A Boot 3 / Java 21 migration is mandatory, not optional, to revive this safely.
- **Frontend confirms the user's instinct** — rebuilding from scratch is the right call. Built on
  deprecated tooling (Create React App), no router, no login screen, one feature (Pagos editing)
  is dead/broken code, and the vacancy-report feature has a live bug. The domain TypeScript
  interfaces and Yup validation rules are worth mining as a spec, but not the code itself.

---

## Backend (`consultorios-back`)

### What's solid
- **Domain model** (`src/main/java/org/tmed/consultoriosback/model/`): `Consultorio` (office),
  `Profesional` (tenant), `ContratoDeAlquiler` (rental contract), `TransaccionDeAlquiler`
  (payment), `AlquilerVacancia` (weekly schedule slot), `Expensa` (expense), `Usuario` (login
  account) — implemented as Java records over Spring Data JDBC. Clean, matches the SQL schema,
  and covers the real business needs.
- **Layered structure** (`controllers/` → `services/` (partial) → `repository/` → `model/`) is
  conventional and easy to navigate.
- **REST surface is close to complete**: CRUD for every entity, plus useful reports (income,
  expenses, vacancy, unpaid contracts by date range) and a scheduled monthly payment-reminder
  email job.
- **Contract overlap-detection** logic (`ContratosController`) is genuine, non-trivial business
  logic already written and worth keeping.
- The ER diagram (`Diagrama de BD.png`, `ALQUILERES_VACANCIA.uml`) and the test-resource SQL
  script (`src/test/resources/db/consultorios_schema_bd.sql`) together document the schema well
  enough to regenerate it.

### What needs fixing before anything else
1. **Security is disabled.** `WebSecurityConfig` has `@EnableWebSecurity` commented out and the
   active filter chain is `.anyRequest().permitAll()` — every endpoint (read, write, delete) is
   open to anyone right now. There's real scaffolding for a login flow (`Usuario`, bcrypt password
   encoding, `UserDetailsService`) but it's disconnected, and even if re-enabled it grants no
   roles/authorities (the `esAdmin` flag on `Usuario` isn't wired to Spring Security at all).
2. **Secrets are committed in plaintext** in `application.properties`: MySQL credentials and a
   Gmail SMTP username + app password. These must be rotated (assume compromised) and moved to
   environment variables / a secrets manager regardless of anything else.
3. **Known bugs in the repository layer** (found by code inspection, not yet reproduced against a
   running DB):
   - `TransaccionesDeAlquilerRepositorio.getTransaccionDeAlquiler()` selects `FROM CONSULTORIOS`
     instead of `TRANSACCIONES_DE_ALQUILERES` — `GET /pagos` likely returns offices, not payments.
   - `TransaccionesDeAlquilerRepositorio.deleteTransaccionDeAlquiler()` soft-deletes a row in
     `CONSULTORIOS` instead of the payments table.
   - `ContratosDeAlquilerRepositorio.getContratosPorNumeroDeConsultorio()` joins `PROFESIONALES`
     on the wrong column (`CDA.ID_CONSULTORIO` instead of `CDA.ID_PROFESIONAL`).
   - `ContratosDeAlquilerRepositorio.getContratosConNombres()` selects a `MONTO_A_PAGAR` column
     that doesn't exist in the schema script — likely a runtime SQL error.
   - `UsuariosRepositorio.findActiveUsers()` joins system users to contracts via professional ID —
     conflates two unrelated concepts (login accounts vs. tenants); this "active users" feature
     looks unfinished/never resolved conceptually.
   - Payments have no working delete endpoint (the handler is commented out and calls a
     nonexistent repository method).
4. **No production schema/migration story.** The only schema-creation SQL lives under
   `src/test/resources`; there's no Flyway/Liquibase and nothing under `src/main/resources` to
   stand up a fresh database.
5. **`ReactAppProxy`** hardcodes `localhost:3000` and drops all request headers — a local-dev
   convenience that shouldn't ship as-is.

### Framework/runtime debt
- Spring Boot **2.7.2** — OSS support ended Nov 2023. Needs to move to Boot 3.x.
- Java **18** (`sourceCompatibility`) — non-LTS, long EOL. Needs 17 or 21 LTS.
- Boot 3 migration means `javax.*` → `jakarta.*` namespace changes (affects mail, servlet,
  logging code).
- Gradle wrapper **8.0.1** — should bump for reliable JDK 21 support.
- `mysql:mysql-connector-java` → rename to `com.mysql:mysql-connector-j`.

### Tests
Minimal: one brittle `@SpringBootTest` that asserts an exact hardcoded JSON body against a
manually pre-seeded local MySQL, and one trivial property-override test. No coverage of the
overlap-detection logic, payments, reports, security, or email. `.http` scratch files exist for
manual testing only.

---

## Frontend (`consultorios-front`)

### Verdict
Agrees with the user's instinct: **rebuild from scratch.** Built on Create React App (now
deprecated/archived), TypeScript 4.7 and axios 0.27 (both old majors), no router at all (tab
switching via local state — refresh always resets to the Pagos tab, no deep links, no back
button), and no login screen (the SPA assumes a session cookie from a separate, non-React login
page served by the backend).

### What's genuinely broken today
- **Pagos (payments) editing is dead code.** The edit dialog is entirely commented out, and the
  form component it would have opened (`PagosFormulario.tsx`) is a leftover copy-paste of the
  *Consultorios* form — wrong title, wrong fields entirely. Payments can currently only be
  created via one fixed flow, never edited.
- **`ContratosSinPagar.tsx`** ("unpaid contracts" screen) is orphaned — fully built but not
  reachable from any tab/route.
- **Vacancy report is broken**: `ReportesAPI.tsx` sends hardcoded date arithmetic
  (`2022 - 1 - 25`) instead of real dates — evaluates to nonsense numbers instead of calling the
  working code (still present, commented out) just above it.
- Chart.js/react-chartjs-2 are installed but unused anywhere — Reportes only renders tables.
- The one existing test (`App.test.tsx`) is unmodified CRA boilerplate asserting text that no
  longer exists in the app — it would fail if run. There is no other test coverage.
- Prettier is configured with `requirePragma: true` but no file has the required pragma comment
  — Prettier is effectively a no-op across the whole codebase as currently configured.

### What's worth carrying forward (as reference/spec, not code)
- The per-entity TypeScript interfaces (`Profesional`, `Consultorio`, `Contrato`, `Expensa`,
  `Usuario`, `Vacancia`) — a clean starting point for an API contract.
- The Yup validation schemas (`EsquemasDeValidacion.ts`) — encode real business rules (contract
  end date after start date, password confirmation, required fields) worth preserving.
- The **Vacancias matrix** (day × room × hour occupancy grid with overlap validation) is the
  most substantial working feature in the app and a good design reference even in a rebuild.
- The list of `*API.ts` files is a ready-made map of every backend endpoint the frontend
  actually calls — useful for scoping API compatibility during the backend upgrade.

### What's working today (functionally, if not stylishly)
Profesionales, Consultorios, Contratos, Expensas, Usuarios (CRUD), and the Vacancias scheduling
matrix. Income/expense reporting works; the vacancy portion of Reportes does not.

---

## Recommended direction

1. **Keep and harden the backend.** Fix the security hole and rotate secrets first (these are
   not optional, even for a personal/internal tool). Then do the Boot 3 / Java LTS upgrade, fix
   the identified repository bugs, and add a real migration tool before touching new features.
2. **Rebuild the frontend from scratch** on current tooling (the user's instinct is correct),
   using the existing domain interfaces, validation rules, and the Vacancias matrix design as a
   spec — not as code to copy in wholesale.
3. Treat this review's open questions (hosting target, single-admin vs. multi-user roles, whether
   the office-image-upload field should finally be implemented, whether the Pagos/Reportes
   bugs should be fixed pre- or post-rebuild) as inputs to the planning file below, to be settled
   with the project owner before implementation starts.

See [REQUIREMENTS.md](REQUIREMENTS.md) for the working requirements/planning document.
