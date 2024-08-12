package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.repository.StockRepository;
import com.slabtech.lamexPartnerTracing.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<Stock> findAllByPartner(Partner thePartner) {
        return stockRepository.findByPartnerOrderByIdDesc(thePartner);
    }

    @Override
    public Stock findStockById(UUID theId) {
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
    public void increaseStockQuantity(UUID id, double amount) {
        Stock stock = stockRepository.findById(id).orElse(null);
            double newBalance = calculateBalance(id);
            stock.setBalance(newBalance);
            stockRepository.save(stock);
    }

    @Override
    public void decreaseStockQuantity(UUID id, double amount) {
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
    public void rechargeStockQuantity(UUID id, double amount) {
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
    public double calculateBalance(UUID id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        Double creditSum = transactionRepository.sumAmountByStockIdAndType(id, "credit");
        Double debitSum = transactionRepository.sumAmountByStockIdAndType(id, "debit");
        Double rechargeSum = transactionRepository.sumAmountByStockIdAndType(id, "recharge");
        creditSum = (creditSum == null) ? 0 : creditSum;
        debitSum = (debitSum == null) ? 0 : debitSum;
        rechargeSum = (rechargeSum == null) ? 0 : rechargeSum;
        double newBalance = (creditSum + rechargeSum) - debitSum;
        stock.setBalance(newBalance);
        stockRepository.save(stock);
        return newBalance;
    }

    @Override
    public double creditSum(UUID id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        return transactionRepository.sumAmountByStockIdAndType(id, "credit");
    }

    @Override
    public double debitSum(UUID id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        return transactionRepository.sumAmountByStockIdAndType(id, "debit");
    }

    @Override
    public double rechargeSum(UUID id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        return transactionRepository.sumAmountByStockIdAndType(id, "recharge");
    }

    @Override
    public int countTransaction(UUID id) {
        return 0;
    }

    @Override
    public long countStockBypartner(Partner thePartner) {
        return stockRepository.countStocksByPartner(thePartner);
    }


}
