package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.ClientDao;
import com.slabtech.lamexPartnerTracing.dao.ReceiptDao;
import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {

    ReceiptDao receiptDao;
    ClientDao clientDao;
    UserDao userDao;

    @Autowired
    public AppController(ReceiptDao theReceiptDao, ClientDao theClientDao, UserDao theUserDao){
        receiptDao = theReceiptDao;
        clientDao = theClientDao;
        userDao = theUserDao;
    }

    @GetMapping("/")
    public String showHome(Model theModel) {
        List<Receipt> theReceipts = receiptDao.findLastsReceipts();
        Receipt theReceipt = new Receipt();
        Long receiptCount = receiptDao.getCount();
        Long clientCount = clientDao.getClientCount();
        Long userCount = userDao.getUserCount();
        theModel.addAttribute("receiptCount", receiptCount);
        theModel.addAttribute("clientCount", clientCount);
        theModel.addAttribute("userCount", userCount);
        theModel.addAttribute("receipts", theReceipts);
        theModel.addAttribute("receipt", theReceipt);
        return "index";
    }
}
