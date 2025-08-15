package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerSalesSumProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    //RELATÓRIO DE VENDAS
    @Query(value = "SELECT obj FROM Seller obj JOIN FETCH obj.sales s " +
            "WHERE s.date BETWEEN :dataInicial  AND :dataFinal " +
            "AND UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Seller obj JOIN obj.sales s " +
                    "WHERE s.date BETWEEN :dataInicial  AND :dataFinal " +
                    "AND UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Seller> searchSellerSales(
             LocalDate dataInicial, LocalDate dataFinal, String name, Pageable pageable);

    //SUMARIO VENDAS POR VENDEDOR
    @Query(nativeQuery = true,
            value = "SELECT TB_SELLER.NAME, SUM(TB_SALES.AMOUNT) AS totalAmount " +
                    "FROM TB_SALES " +
                    "INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID " +
                    "WHERE TB_SALES.DATE BETWEEN :dataInicial AND :dataFinal " +
                    "GROUP BY TB_SELLER.NAME")
    List<SellerSalesSumProjection> searchSellerSalesSum(
            LocalDate dataInicial, LocalDate dataFinal);
}
