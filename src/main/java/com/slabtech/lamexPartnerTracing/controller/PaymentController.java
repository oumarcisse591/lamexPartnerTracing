package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.exception.NullAmountException;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import com.slabtech.lamexPartnerTracing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class PaymentController {

//    public static String UPLOAD_DIRECTORY = "/Users/admin/Downloads/";
    public static String UPLOAD_DIRECTORY = "/opt/AppStorage/IdCard/";
//    private String uploadDirectory = "/Users/admin/Downloads/";
    public static String uploadDirectory = "/opt/AppStorage/Signature/";
    private PaymentService paymentService;

    private StockService stockService;

    private UserService userService;

    @Autowired
    public PaymentController(PaymentService thePaymentService, StockService theStockService, UserService theUserService){
        paymentService = thePaymentService;
        stockService = theStockService;
        userService =theUserService;
    }

    @GetMapping("/payment-list")
    public String listPayment(Model theModel){
        List<Payment> payments = paymentService.findAllPaymentDesc();
        theModel.addAttribute("payments", payments);
        return "payment/list-payment";
    }

    @GetMapping("/add-payment")
    public String addPayment(Model theModel){
        Payment payment = new Payment();
        List<Stock> stocks = stockService.findAllStock();
        theModel.addAttribute("payment", payment);
        theModel.addAttribute("stocks", stocks);
        return "payment/add-payment";
    }

    @GetMapping("/add-signature")
    public String addSignature(@RequestParam("id") int theId, Model theModel){
        Payment payment = paymentService.findPaymentById(theId);
        theModel.addAttribute("payment",payment);
        return "payment/add-signature";
    }
    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute("payment") Payment thePayment, @RequestParam("image") MultipartFile file, @RequestParam("clientName") String clientName, @RequestParam("signature_agent") String signatureDataURL, RedirectAttributes redirectAttributes) throws MessagingException, IOException {

        try{
            String base64Image = signatureDataURL.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            bis.close();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();


            LocalDate today = LocalDate.now();
            Date now = new Date();
            String date = today.format(DateTimeFormatter.ofPattern("yy"));
            String month = today.format(DateTimeFormatter.ofPattern("MM"));
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            String code = "PAY" + month + date + "-" + randomNumber;

            File outputfile = new File(uploadDirectory + "/" + code + userName + ".png");
            ImageIO.write(bufferedImage, "png", outputfile);

            String newFileName = clientName + code +".jpg";
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newFileName);
            Files.write(fileNameAndPath, file.getBytes());

            thePayment.setReason("Paiement Client");
            thePayment.setReferenceTransaction(code);
            thePayment.setTransactionDate(now);
            thePayment.setTransactionType("debit");
            thePayment.setPhoto(UPLOAD_DIRECTORY + newFileName);
            User user = userService.findByUserName(userName);
            thePayment.setUser(user);
            thePayment.setSignatureAgent(uploadDirectory + "/" + code + userName + ".png");

            Payment theRegisteredPayment = paymentService.savePayment(thePayment);
            int id = theRegisteredPayment.getIdTransaction();
            redirectAttributes.addFlashAttribute("message","Le paiement a ete effectue avec succes");
            return "redirect:/add-signature?id=" + id;
        } catch (InsufficientStockException | NullAmountException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/add-payment";
        }
    }

    @PostMapping("/save-paymentSignature")
    public String saveSignature(@RequestParam("idTransaction") int theId, Model theModel , @RequestParam("signature") String signatureDataURL,  RedirectAttributes redirectAttributes) throws MessagingException, IOException {

        if (signatureDataURL != null && signatureDataURL.contains(",")){
            String base64Image = signatureDataURL.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            bis.close();

            // Save the image to a file
            Payment thePayment = paymentService.findPaymentById(theId);
            String reference = thePayment.getReferenceTransaction();
            File outputfile = new File(uploadDirectory + "/" + reference + ".png");
            ImageIO.write(bufferedImage, "png", outputfile);
            thePayment.setIdTransaction(thePayment.getIdTransaction());
            thePayment.setSignature(thePayment.getSignature());
            thePayment.setTransactionAmount(thePayment.getTransactionAmount());
            thePayment.setClientAddress(thePayment.getClientAddress());
            thePayment.setUser(thePayment.getUser());
            thePayment.setPhoto(thePayment.getPhoto());
            thePayment.setClientIdCard(thePayment.getClientIdCard());
            thePayment.setClientName(thePayment.getClientName());
            thePayment.setReason(thePayment.getReason());
            thePayment.setPartner(thePayment.getPartner());
            thePayment.setStock(thePayment.getStock());
            thePayment.setClientPhone(thePayment.getClientPhone());
            thePayment.setTransactionType(thePayment.getTransactionType());
            thePayment.setTransactionDate(thePayment.getTransactionDate());
            thePayment.setReferenceTransaction(thePayment.getReferenceTransaction());
            thePayment.setSignatureAgent(thePayment.getSignatureAgent());
            thePayment.setSignature(uploadDirectory + "/" + reference + ".png");
            paymentService.savePayment(thePayment);
        } else{
            redirectAttributes.addFlashAttribute("message","signature non valide");
            return "redirect:/payment-list";
        }
        redirectAttributes.addFlashAttribute("message","Paiement effectue");
        return "redirect:/payment-list";
    }
}
