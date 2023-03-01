package org.tmed.consultoriosback.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tmed.consultoriosback.model.Expensa;

import java.util.Optional;

@Repository
public interface ExpensasRepositorio extends CrudRepository<Expensa, Long> {

    @Query("SELECT * FROM EXPENSAS WHERE OCULTO = 0")
    Iterable<Expensa> getExpensas();

    @Query("""
            SELECT  sum(E.CANTIDAD)
            FROM EXPENSAS E
            WHERE MONTH(E.FECHA_DE_PAGO) = MONTH(:mes) 
            AND YEAR(E.FECHA_DE_PAGO) = YEAR(:mes)
               """)
    Optional<Double> getExoensasPorMes(@Param("mes") String mes);

    @Modifying
    @Query("UPDATE EXPENSAS SET OCULTO = 1 WHERE ID = :id")
    void deleteExpensa(@Param("id") long id);
}
