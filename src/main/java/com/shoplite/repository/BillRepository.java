package com.shoplite.repository;

import com.shoplite.entity.Bill;
import com.shoplite.entity.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByStatus(BillStatus status);

    @Query("SELECT b FROM Bill b " +
           "WHERE b.status = com.shoplite.entity.BillStatus.FINALIZED " +
           "AND b.finalizedAt >= :startOfDay AND b.finalizedAt <= :endOfDay")
    List<Bill> findFinalizedBillsBetween(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT b FROM Bill b " +
           "WHERE b.billDate >= :startOfDay AND b.billDate <= :endOfDay")
    List<Bill> findBillsByDateBetween(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    long countByStatus(BillStatus status);

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.status = com.shoplite.entity.BillStatus.FINALIZED")
    BigDecimal sumTotalRevenue();
}
