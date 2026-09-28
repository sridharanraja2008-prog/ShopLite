package com.shoplite.repository;

import com.shoplite.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    boolean existsByProductId(Long productId);

    @Query("SELECT bi.product.id, bi.product.name, SUM(bi.quantity) " +
           "FROM BillItem bi " +
           "WHERE bi.bill.status = com.shoplite.entity.BillStatus.FINALIZED " +
           "GROUP BY bi.product.id, bi.product.name " +
           "ORDER BY SUM(bi.quantity) DESC")
    List<Object[]> findTopSellingProducts();
}
