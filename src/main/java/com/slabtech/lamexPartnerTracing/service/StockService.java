package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Stock;

import java.util.List;

public interface StockService {

    List<Stock> findAllStock();
    Stock findStockById(int theId);

    Stock saveStock(Stock stock);

    void increaseStockQuantity(int id, double amount);

    void decreaseStockQuantity(int id, double amount);

    void rechargeStockQuantity(int id, double amount);

    long countAllStocks();

    long countAllDisabledStocks();

    double calculateBalance(int id);


}
