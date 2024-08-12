package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Stock;

import java.util.List;
import java.util.UUID;

public interface StockService {

    List<Stock> findAllStock();

    /**
     * Recuperer la liste de stock pour un partenaire
     * @param thePartner
     * @return
     */
    List<Stock> findAllByPartner(Partner thePartner);


    Stock findStockById(UUID theId);

    Stock saveStock(Stock stock);

    void increaseStockQuantity(UUID id, double amount);

    void decreaseStockQuantity(UUID id, double amount);

    void rechargeStockQuantity(UUID id, double amount);

    long countAllStocks();

    long countAllDisabledStocks();

    double calculateBalance(UUID id);

    double creditSum(UUID id);
    double debitSum(UUID id);
    double rechargeSum(UUID id);
     int countTransaction(UUID id);

     long countStockBypartner(Partner thePartner);

}
