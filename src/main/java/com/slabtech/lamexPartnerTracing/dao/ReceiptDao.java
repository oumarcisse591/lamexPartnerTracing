package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Receipt;

import java.util.List;

public interface ReceiptDao {

    List<Receipt> findAllReceipt();

    List<Receipt> findLastsReceipts();

    Receipt findFirstByOrderByUdDesc();
    Receipt findByReceiptName(String userName);

    Long getCount();

    Receipt findReceiptById(int theId);

    void deleteReceiptById(int theId);

    Receipt saveReceipt(Receipt theReceipt);
}

