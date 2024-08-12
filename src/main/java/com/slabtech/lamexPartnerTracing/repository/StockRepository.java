package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    List<Stock> findAllByOrderByIdDesc();

    @Query("SELECT COUNT(s) FROM Stock s")
    long countAllStocks();

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.enabled = false")
    long countInactiveStocks();

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.partner = :partner")
    long countStocksByPartner(@Param("partner") Partner partner);

    /**
     * Recuperer la liste de stock liee au partenaire
     * @param partner
     * @return
     */
    List<Stock> findByPartnerOrderByIdDesc(Partner partner);

}
