package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query("SELECT * FROM expenses WHERE active = true")
    Iterable<Expense> findActive();

    @Modifying
    @Query("UPDATE expenses SET active = false WHERE id = :id")
    void deactivate(@Param("id") long id);
}
