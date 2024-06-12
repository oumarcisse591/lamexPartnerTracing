package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findAllByOrderByIdDesc();

    @Query("SELECT COUNT(s) FROM Stock s")
    long countAllStocks();

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.enabled = false")
    long countInactiveStocks();
}
