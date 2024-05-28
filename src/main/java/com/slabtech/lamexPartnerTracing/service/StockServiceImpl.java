package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{

    private StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository theStockRepository){
        stockRepository = theStockRepository;
    }
    @Override
    public List<Stock> findAllStock() {
        return stockRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Stock findStockById(int theId) {
        Optional<Stock> result = stockRepository.findById(theId);
        Stock theStock = null;

        if (result.isPresent()){
            theStock = result.get();
        }
        else {
            throw new RuntimeException("Did not find the Stock with id" + theId);
        }
        return theStock;
    }

    @Override
    public Stock saveStock(Stock theStock) {
        return stockRepository.save(theStock);
    }

    @Override
    public void increaseStockQuantity(int id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null){
            stock.setBalance(stock.getBalance() + (amount));
            stockRepository.save(stock);
        }
    }

    @Override
    public void decreaseStockQuantity(int id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null ){
            if (stock.getBalance() < amount){
                throw new InsufficientStockException("Stock insuffisant pour cette operation ");
            }
            stock.setBalance(stock.getBalance() - (amount));
            stockRepository.save(stock);
        }
    }

    @Override
    public void rechargeStockQuantity(int id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null){
            stock.setBalance(stock.getBalance() + (amount));
            stockRepository.save(stock);
        }
    }

    @Override
    public long countAllStocks() {
        return stockRepository.countAllStocks();
    }

}
