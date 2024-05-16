package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.BeneficiaryDao;
import com.slabtech.lamexPartnerTracing.dao.ClientDao;
import com.slabtech.lamexPartnerTracing.dao.ReceiptDao;
import com.slabtech.lamexPartnerTracing.dao.SocietyDao;
import com.slabtech.lamexPartnerTracing.entity.Beneficiary;
import com.slabtech.lamexPartnerTracing.entity.Client;
import com.slabtech.lamexPartnerTracing.entity.Receipt;
import com.slabtech.lamexPartnerTracing.entity.Society;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ReceiptController {


//    JavaMailSender javaMailSender;
    private ReceiptDao receiptDao;
    private ClientDao clientDao;
    private SocietyDao societyDao;

    private BeneficiaryDao beneficiaryDao;


    @Autowired
    public ReceiptController(ReceiptDao theReceiptDao, ClientDao theClientDao, SocietyDao theSocietyDao, BeneficiaryDao theBeneficiaryDao){

        receiptDao = theReceiptDao;
        clientDao = theClientDao;
        societyDao = theSocietyDao;
        beneficiaryDao = theBeneficiaryDao;
    }

    @GetMapping("/list-receipt")
    public String getReceiptList(Model theModel){
        List<Receipt> theReceipts = receiptDao.findAllReceipt();
        Receipt theReceipt = new Receipt();
        theModel.addAttribute("receipts", theReceipts);
        theModel.addAttribute("receipt", theReceipt);
        return "receipt/list-receipt";
    }

    @GetMapping("/get-receipt")
    public String getReceipt(@RequestParam("id") int theId, Model theModel){
        Receipt theReceipt = receiptDao.findReceiptById(theId);
        theModel.addAttribute("receipt", theReceipt);
        return "receipt/recepisse";
    }
    @GetMapping("/update-receipt")
    public String updateReceipt(@RequestParam("id") int theId, Model theModel){
        Receipt theReceipt = receiptDao.findReceiptById(theId);
        List<Client> theClients = clientDao.findAllClient();
        List<Society> theSocieties = societyDao.findAllSociety();
        List<Beneficiary> theBeneficiaries = beneficiaryDao.findAllBeneficiary();
        theModel.addAttribute("beneficiaries", theBeneficiaries);
        theModel.addAttribute("receipt", theReceipt);
        theModel.addAttribute("clients", theClients);
        theModel.addAttribute("societies", theSocieties);
        return "receipt/update-receipt";
    }

    @GetMapping("/add-receipt")
    public String addReceipt(Model theModel){
        Receipt theReceipt = new Receipt();
        Society theSociety = new Society();
        List<Client> theClients = clientDao.findAllClient();
        List<Society> theSocieties = societyDao.findAllSociety();
        List<Beneficiary> theBeneficiaries = beneficiaryDao.findAllBeneficiary();
        theModel.addAttribute("beneficiaries", theBeneficiaries);
        theModel.addAttribute("society", theSociety);
        theModel.addAttribute("societies", theSocieties);
        theModel.addAttribute("clients", theClients);
        theModel.addAttribute("receipt", theReceipt);
        return "receipt/ajout-receipt";
    }

    @PostMapping("/save-receipt")
    public String saveReceipt(@ModelAttribute("receipt") Receipt theReceipt, RedirectAttributes redirectAttributes) throws MessagingException {
        LocalDate today = LocalDate.now();
        Date now = new Date();
        String date = today.format(DateTimeFormatter.ofPattern("yy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));

//        Receipt lastReceipt = receiptDao.findFirstByOrderByUdDesc();
//        int nextId = lastReceipt.getId() + 1;

        // Create a random object
        Random random = new Random();

        // Generate a random number between 100000 and 999999
        int randomNumber = random.nextInt(900000) + 100000;
        String code = "LMX" + month + date + "-" + randomNumber;
        theReceipt.setReceiptCode(code);
        theReceipt.setReceiptDate(now);
        theReceipt.setEnabled(true);
//        sendSimpleMessage("oumarcisse202@gmail.com","TT APPLICATION TRANSFERT CONFIRMATION","Vous avez initie un transfert. reference : " +code+ " Date : " + now+ ". Si vous n'etes pas auteur de ce transfert, Connectez-vous a l'application pour bloquer.");
        receiptDao.saveReceipt(theReceipt);
        redirectAttributes.addFlashAttribute("message", "La transaction a été enregistrée avec succès");
        return "redirect:/list-receipt";
    }

    @PostMapping("/saveupdate-receipt")
    public String saveUpdateReceipt(@ModelAttribute("receipt") Receipt theReceipt, RedirectAttributes redirectAttributes) throws MessagingException {
        theReceipt.setEnabled(true);
        String code = theReceipt.getReceiptCode();
//        sendSimpleMessage("oumarcisse202@gmail.com","TT APPLICATION MODIFICATION","Le transfert avec le numero de reference : " +code+   " vient d'etre modifier. Si vous n'etes pas auteur de cette modification, Connectez-vous a l'application pour bloquer.");
        receiptDao.saveReceipt(theReceipt);
        redirectAttributes.addFlashAttribute("message", "Le recu a été modifié");
        return "redirect:/list-receipt";
    }

    @GetMapping("/delete-receipt")
    public String deleteReceipt(@RequestParam("id") int theId, Model theModel , RedirectAttributes redirectAttributes){
        Receipt theReceipt = receiptDao.findReceiptById(theId);
        theReceipt.setId(theReceipt.getId());
        theReceipt.setReceiptCode(theReceipt.getReceiptCode());
        theReceipt.setSwift_code(theReceipt.getSwift_code());
        theReceipt.setReceiptDate(theReceipt.getReceiptDate());
        theReceipt.setValueDate(theReceipt.getValueDate());
        theReceipt.setAmount(theReceipt.getAmount());
        theReceipt.setCurrency(theReceipt.getCurrency());
        theReceipt.setBeneficiary(theReceipt.getBeneficiary());
        theReceipt.setBeneficiaryAccount(theReceipt.getBeneficiaryAccount());
        theReceipt.setBeneficiaryBankAddress(theReceipt.getBeneficiaryBankAddress());
        theReceipt.setRemitterBank(theReceipt.getRemitterBank());
        theReceipt.setRemitterAccount(theReceipt.getRemitterAccount());
        theReceipt.setMotifTransaction(theReceipt.getMotifTransaction());
        theReceipt.setClient(theReceipt.getClient());
        theReceipt.setSociety(theReceipt.getSociety());
        theReceipt.setBeneficiary(theReceipt.getBeneficiary());
        theReceipt.setEnabled(false);
        receiptDao.saveReceipt(theReceipt);
        redirectAttributes.addFlashAttribute("message", "Le ticket a été supprimé");
        return "redirect:/list-receipt";
    }



//    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("ttapplicationlamex@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        javaMailSender.send(message);
//    }
}
