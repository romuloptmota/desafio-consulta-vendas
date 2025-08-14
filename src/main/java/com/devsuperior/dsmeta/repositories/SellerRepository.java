package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(value = "SELECT obj FROM Seller obj JOIN FETCH obj.sales s " +
            "WHERE s.date BETWEEN :dataInicial  AND :dataFinal " +
            "AND UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Seller obj JOIN obj.sales s " +
                    "WHERE s.date BETWEEN :dataInicial  AND :dataFinal " +
                    "AND UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Seller> searchSellerSales(
             LocalDate dataInicial, LocalDate dataFinal, String name, Pageable pageable);
}
