package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.Payment;
import com.slabtech.lamexPartnerTracing.entity.Stock;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.exception.InsufficientStockException;
import com.slabtech.lamexPartnerTracing.exception.NullAmountException;
import com.slabtech.lamexPartnerTracing.repository.UserRepository;
import com.slabtech.lamexPartnerTracing.service.PartnerService;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import com.slabtech.lamexPartnerTracing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import javax.mail.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PaymentController {

//    public static String UPLOAD_DIRECTORY = "/Users/admin/Downloads/";
    public static String UPLOAD_DIRECTORY = "/opt/AppStorage/IdCard/";
//    private String uploadDirectory = "/Users/admin/Downloads/";
    public static String uploadDirectory = "/opt/AppStorage/Signature/";
    private PaymentService paymentService;

    private StockService stockService;

    private UserService userService;

    private UserRepository userRepository;

    private PartnerService partnerService;

    @Autowired
    public PaymentController(PaymentService thePaymentService, StockService theStockService, UserService theUserService, UserRepository theUserRepository, PartnerService thePartnerService){
        paymentService = thePaymentService;
        stockService = theStockService;
        userService =theUserService;
        userRepository = theUserRepository;
        partnerService = thePartnerService;
    }

    @GetMapping("/payment-list")
    public String listPayment(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "50") int size,
                              Model theModel){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = currentUser.getPartner();

        Page<Payment> payments = paymentService.findAllByPartner(partner, PageRequest.of(page, size));
        theModel.addAttribute("payments", payments.getContent());
        theModel.addAttribute("currentPage", page);
        theModel.addAttribute("totalPages", payments.getTotalPages());
        return "payment/list-payment";
    }

    @GetMapping("/payment-list-partner")
    public String listPaymentPartner(@RequestParam("id") UUID theId,
                                     @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "50") int size,
                              Model theModel){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = partnerService.findPartnerById(theId);

        Page<Payment> payments = paymentService.findAllByPartner(partner, PageRequest.of(page, size));
        theModel.addAttribute("payments", payments.getContent());
        theModel.addAttribute("currentPage", page);
        theModel.addAttribute("totalPages", payments.getTotalPages());
        theModel.addAttribute("partnerName", partner.getPartnerName());
        return "payment/list-payment-partner";
    }

    @GetMapping("/add-payment")
    public String addPayment(Model theModel){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Récupérer l'utilisateur courant à partir du nom d'utilisateur
        User currentUser = userRepository.findByUserName(userName);

        // Récupérer le partenaire associé à l'utilisateur
        Partner partner = currentUser.getPartner();

        Payment payment = new Payment();
        List<Stock> stocks = stockService.findAllByPartner(partner);
        Map<UUID, Double> stockBalances = stocks.stream()
                .collect(Collectors.toMap(
                        Stock::getId,
                        stock -> stockService.calculateBalance(stock.getId())
                ));
        theModel.addAttribute("payment", payment);
        theModel.addAttribute("stocks", stocks);
        theModel.addAttribute("stockBalances", stockBalances);
        return "payment/add-payment";
    }

    @GetMapping("/add-signature")
    public String addSignature(@RequestParam("id") UUID theId, Model theModel){
        Payment payment = paymentService.findPaymentById(theId);
        theModel.addAttribute("payment",payment);
        return "payment/add-signature";
    }
    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute("payment") Payment thePayment, @RequestParam("image") MultipartFile file, @RequestParam("clientName") String clientName, @RequestParam("signature_agent") String signatureDataURL, RedirectAttributes redirectAttributes) throws MessagingException, IOException {

        try {

            // Décodage de l'image de la signature à partir du base64
            String base64Image = signatureDataURL.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            BufferedImage bufferedImage;

            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                bufferedImage = ImageIO.read(bis);
            }

            // Récupération de l'utilisateur authentifié
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Génération du code de transaction
            LocalDate today = LocalDate.now();
            String date = today.format(DateTimeFormatter.ofPattern("yy"));
            String month = today.format(DateTimeFormatter.ofPattern("MM"));
            String code = String.format("PAY%s%s-%06d", month, date, new Random().nextInt(900000) + 100000);

            // Enregistrement de l'image de signature
            String signatureFileName = code + userName + ".png";
            File outputfile = new File(uploadDirectory, signatureFileName);
            ImageIO.write(bufferedImage, "png", outputfile);

            // Enregistrement de la photo de paiement
            String newFileName = clientName + code + ".jpg";
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newFileName);
            Files.write(fileNameAndPath, file.getBytes());

            // Récupération de l'utilisateur et du partenaire associés
            User user = userService.findByUserName(userName);
            Partner partner = user.getPartner();

            // Conversion de devise si nécessaire
            double amount = thePayment.getTransactionAmount();
            if ("AED".equals(thePayment.getCurrencyPayment())) {
                amount = amount / 3.67; // Conversion en USD
                thePayment.setTransactionAmount(amount);
            }

            // Configuration des détails du paiement
            thePayment.setReason("Paiement Client");
            thePayment.setReferenceTransaction(code);
            thePayment.setTransactionDate(new Date());
            thePayment.setTransactionType("debit");
            thePayment.setPhoto(UPLOAD_DIRECTORY + newFileName); // Stocker seulement le nom du fichier, pas tout le chemin
            thePayment.setPartner(partner);
            thePayment.setUser(user);
            thePayment.setSignatureAgent(uploadDirectory + signatureFileName);

            // Sauvegarde du paiement
            Payment theRegisteredPayment = paymentService.savePayment(thePayment);
            UUID id = theRegisteredPayment.getIdTransaction();

            // Redirection avec un message de succès
            redirectAttributes.addFlashAttribute("message", "Le paiement a été effectué avec succès");
            return "redirect:/add-signature?id=" + id;

        } catch (InsufficientStockException | NullAmountException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/add-payment";
        }
    }

    @PostMapping("/save-paymentSignature")
    public String saveSignature(@RequestParam("idTransaction") UUID theId, Model theModel , @RequestParam("signature") String signatureDataURL,  RedirectAttributes redirectAttributes) throws MessagingException, IOException {

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
            thePayment.setTransactionAmount(thePayment.getTransactionAmount());
            thePayment.setClientAddress(thePayment.getClientAddress());
            thePayment.setUser(thePayment.getUser());
            thePayment.setPhoto(thePayment.getPhoto());
            thePayment.setClientIdCard(thePayment.getClientIdCard());
            thePayment.setClientName(thePayment.getClientName());
            thePayment.setReason(thePayment.getReason());
            thePayment.setPartner(thePayment.getPartner());
            thePayment.setStock(thePayment.getStock());
            thePayment.setCurrencyPayment(thePayment.getCurrencyPayment());
            thePayment.setClientPhone(thePayment.getClientPhone());
            thePayment.setTransactionType(thePayment.getTransactionType());
            thePayment.setTransactionDate(thePayment.getTransactionDate());
            thePayment.setReferenceTransaction(thePayment.getReferenceTransaction());
            thePayment.setSignatureAgent(thePayment.getSignatureAgent());
            thePayment.setPartner(thePayment.getPartner());
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
