package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import com.slabtech.lamexPartnerTracing.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{

    private StockRepository stockRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public StockServiceImpl(StockRepository theStockRepository, TransactionRepository theTransactionRepository){
        stockRepository = theStockRepository;
        transactionRepository = theTransactionRepository;
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
            double newBalance = calculateBalance(id);
            stock.setBalance(newBalance);
            stockRepository.save(stock);
    }

    @Override
    public void decreaseStockQuantity(int id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null ){
            if (stock.getBalance() < amount){
                throw new InsufficientStockException("Stock insuffisant pour cette operation ");
            }
            double newBalance = calculateBalance(id);
            stock.setBalance(newBalance);
            stockRepository.save(stock);
        }
    }

    @Override
    public void rechargeStockQuantity(int id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null){
            double newBalance = calculateBalance(id);
            stock.setBalance(newBalance);
            stockRepository.save(stock);
        }
    }

    @Override
    public long countAllStocks() {
        return stockRepository.countAllStocks();
    }

    @Override
    public long countAllDisabledStocks() {
        return stockRepository.countInactiveStocks();
    }

    @Override
    public double calculateBalance(int id) {
        Double creditSum = transactionRepository.sumAmountByStockIdAndType(id, "credit");
        Double debitSum = transactionRepository.sumAmountByStockIdAndType(id, "debit");
        Double rechargeSum = transactionRepository.sumAmountByStockIdAndType(id, "recharge");

        creditSum = (creditSum == null) ? 0 : creditSum;
        debitSum = (debitSum == null) ? 0 : debitSum;
        rechargeSum = (rechargeSum == null) ? 0 : rechargeSum;

        return (creditSum + rechargeSum) - debitSum;
    }


}
