package com.slabtech.lamexPartnerTracing.controller;

import com.slabtech.lamexPartnerTracing.dao.ReceiptDao;
import com.slabtech.lamexPartnerTracing.entity.*;
import com.slabtech.lamexPartnerTracing.repository.PaymentRepository;
import com.slabtech.lamexPartnerTracing.repository.TransactionRepository;
import com.slabtech.lamexPartnerTracing.service.MovementService;
import com.slabtech.lamexPartnerTracing.service.PaymentService;
import com.slabtech.lamexPartnerTracing.service.StockService;
import com.slabtech.lamexPartnerTracing.service.TransactionService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ReportController {

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MovementService movementService;

    @Autowired
    private StockService stockService;

    @Autowired
    private TransactionService transactionService;

    private final ResourceLoader resourceLoader;

    public ReportController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


//    private ReportService reportService;

    @GetMapping("/reportPdf")
    public ResponseEntity getReceiptReport(@RequestParam("id") int theId, Model theModel) throws JRException, IOException {
        Receipt theReceipt = receiptDao.findReceiptById(theId);
        String ref = theReceipt.getReceiptCode();
        Date date = theReceipt.getReceiptDate();
        String remitterName = theReceipt.getSociety().getSocietyName();
        String remitterAddress = theReceipt.getSociety().getSocietyAdress();
        String beneficiaryName = theReceipt.getBeneficiary().getBeneficiaryName();
        String beneficiaryAddress = theReceipt.getBeneficiary().getBeneficiaryAdress();
        String beneficiaryBank = theReceipt.getBeneficiaryBank();
        String beneficiaryBankAddress = theReceipt.getBeneficiaryBankAddress();
        String beneficiaryAccount = theReceipt.getBeneficiaryAccount();
        String swift = theReceipt.getSwift_code();
        double amount = theReceipt.getAmount();
        String currency = theReceipt.getCurrency();
        Date valueDate = theReceipt.getValueDate();
        String paymentReference = theReceipt.getMotifTransaction();
        ByteArrayOutputStream reportStream = generateReport(ref, date, remitterName, remitterAddress, beneficiaryName, beneficiaryAddress, beneficiaryBank, beneficiaryBankAddress, beneficiaryAccount, swift, amount, currency, valueDate, paymentReference);
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ref + ".pdf");
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    public ByteArrayOutputStream generateReport(String receiptCode, Date date, String remitterName, String remitterAddress, String BeneficiaryName, String beneficiaryAddress, String beneficiaryBank, String beneficiaryBankAddress, String beneficiaryAccount, String swift, double amount, String currency, Date valueDate, String paymentReference) throws IOException, JRException {
//        File file = ResourceUtils.getFile("classpath:recu.jrxml");

//        Resource resource = new ClassPathResource("recu.jrxml");

        // Obtenir un objet File à partir de la ressource
//        File file = resource.getFile();


        File file = ResourceUtils.getFile("/opt/tomcat/webapps/LamexPartnerTracing/WEB-INF/classes/recu.jrxml");
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("receiptCode", receiptCode);
        parameters.put("receiptDate", date);
        parameters.put("remitterName", remitterName);
        parameters.put("remitterAddress", remitterAddress);
        parameters.put("beneficiaryName", BeneficiaryName);
        parameters.put("beneficiaryAddress", beneficiaryAddress);
        parameters.put("beneficiaryBank", beneficiaryBank);
        parameters.put("beneficiaryBankAddress", beneficiaryBankAddress);
        parameters.put("beneficiaryAccount", beneficiaryAccount);
        parameters.put("swift", swift);
        parameters.put("amount", amount);
        parameters.put("currency", currency);
        parameters.put("valueDate", valueDate);
        parameters.put("paymentReference", paymentReference);

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    public ByteArrayOutputStream generatePaymentReport(String ref, Date date, String partnerCode, String partnerName, String partnerPhone, String clientName, String clientAddress, String clientIdCard, String clientPhone, double amount, double amountAED, String photo, String remark, String country, String currencyPayment, String signature, String signatureAgent, String username) throws IOException, JRException {
//        File file = ResourceUtils.getFile("classpath:payment-receipt-office.jrxml");
        Locale locale = new Locale("en", "US");

//        Resource resource = new ClassPathResource("payment-receipt.jrxml");

        // Obtenir un objet File à partir de la ressource
//        File file = resource.getFile();


        File file = ResourceUtils.getFile("/opt/tomcat/webapps/LamexPartnerTracing/WEB-INF/classes/payment-receipt-office.jrxml");
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("ref", ref);
        parameters.put("datePayment", date);
        parameters.put("date", date);
        parameters.put("partnerCode", partnerCode);
        parameters.put("partnerName", partnerName);
        parameters.put("partnerPhone", partnerPhone);
        parameters.put("clientName", clientName);
        parameters.put("clientAddress", clientAddress);
        parameters.put("clientIdCard", clientIdCard);
        parameters.put("clientPhone", clientPhone);
        parameters.put("amount", amount);
        parameters.put("amountAED", amountAED);
        parameters.put("photo",(photo != null && !photo.isEmpty()) ? photo : null);
        parameters.put("remark",(remark != null && !remark.isEmpty()) ? remark : "Aucune remarque");
        parameters.put("stockCountry",(country != null && !country.isEmpty()) ? country : "Not specified");
        parameters.put("currencyPayment",(currencyPayment != null && !currencyPayment.isEmpty()) ? currencyPayment : "Not specified");
        parameters.put("username", username);
        parameters.put("signature", (signature != null && !signature.isEmpty()) ? signature : null);
        parameters.put("signatureAgent", (signatureAgent != null && !signatureAgent.isEmpty()) ? signatureAgent : null);
        parameters.put( JRParameter.REPORT_LOCALE, locale );

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    @GetMapping("/paymentReportPdf")
    public ResponseEntity paymentReport(@RequestParam("id") UUID theId, Model theModel) throws JRException, IOException{
        Payment thePayment = paymentService.findPaymentById(theId);

        String ref = thePayment.getReferenceTransaction();
        Date datePayment = thePayment.getTransactionDate();
        String partnerCode = thePayment.getStock().getPartner().getPartnerCode();
        String partnerName = thePayment.getStock().getPartner().getPartnerName();
        String partnerPhone = thePayment.getStock().getPartner().getPartnerPhone();
        String country = thePayment.getStock().getCountry().getCountryName();
        String currencyPayment = thePayment.getCurrencyPayment();
        String clientName = thePayment.getClientName();
        String clientAddress = thePayment.getClientAddress();
        String clientIdCard = thePayment.getClientIdCard();
        String clientPhone = thePayment.getClientPhone();
        double amount = thePayment.getTransactionAmount();
        double amountAED = thePayment.getTransactionAmount() * 3.67;
        String photo = thePayment.getPhoto();
        String remark = thePayment.getRemark();
        String signature = thePayment.getSignature();
        String signatureAgent = thePayment.getSignatureAgent();
        String username = thePayment.getUser().getName();

        ByteArrayOutputStream reportStream = generatePaymentReport(ref, datePayment, partnerCode, partnerName, partnerPhone, clientName, clientAddress, clientIdCard, clientPhone, amount, amountAED, photo, remark, country, currencyPayment, signature, signatureAgent, username);
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ref + "-" +clientName+".pdf");
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);


    }

    // La Facture du Client

    public ByteArrayOutputStream generatePaymentReportClient(String ref, Date date, String partnerCode, String partnerName, String partnerPhone, String clientName, String clientAddress, String clientIdCard, String clientPhone, double amount, double amountAED, String photo, String remark, String currencyPayment, String country, String signature, String signatureAgent,  String username) throws IOException, JRException {
//        File file = ResourceUtils.getFile("classpath:payment-receipt.jrxml");
        Locale locale = new Locale("en", "US");

//        Resource resource = new ClassPathResource("payment-receipt.jrxml");

        // Obtenir un objet File à partir de la ressource
//        File file = resource.getFile();


        File file = ResourceUtils.getFile("/opt/tomcat/webapps/LamexPartnerTracing/WEB-INF/classes/payment-receipt.jrxml");
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("ref", ref);
        parameters.put("datePayment", date);
        parameters.put("date", date);
        parameters.put("partnerCode", partnerCode);
        parameters.put("partnerName", partnerName);
        parameters.put("partnerPhone", partnerPhone);
        parameters.put("clientName", clientName);
        parameters.put("clientAddress", clientAddress);
        parameters.put("clientIdCard", clientIdCard);
        parameters.put("clientPhone", clientPhone);
        parameters.put("amount", amount);
        parameters.put("amountAED", amountAED);
        parameters.put("photo",(photo != null && !photo.isEmpty()) ? photo : null);
        parameters.put("remark",(remark != null && !remark.isEmpty()) ? remark : "Aucune remarque");
        parameters.put("stockCountry",(country != null && !country.isEmpty()) ? country : "Not specified");
        parameters.put("currencyPayment",(currencyPayment != null && !currencyPayment.isEmpty()) ? currencyPayment : "Not specified");
        parameters.put("username", username);
        parameters.put("signature", (signature != null && !signature.isEmpty()) ? signature : null);
        parameters.put("signatureAgent", (signatureAgent != null && !signatureAgent.isEmpty()) ? signatureAgent : null);
        parameters.put( JRParameter.REPORT_LOCALE, locale );

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    @GetMapping("/paymentReportClientPdf")
    public ResponseEntity paymentReportClient(@RequestParam("id") UUID theId, Model theModel) throws JRException, IOException{
        Payment thePayment = paymentService.findPaymentById(theId);

        String ref = thePayment.getReferenceTransaction();
        Date datePayment = thePayment.getTransactionDate();
        String partnerCode = thePayment.getStock().getPartner().getPartnerCode();
        String partnerName = thePayment.getStock().getPartner().getPartnerName();
        String partnerPhone = thePayment.getStock().getPartner().getPartnerPhone();
        String country = thePayment.getStock().getCountry().getCountryName();
        String currencyPayment = thePayment.getCurrencyPayment();
        String clientName = thePayment.getClientName();
        String clientAddress = thePayment.getClientAddress();
        String clientIdCard = thePayment.getClientIdCard();
        String clientPhone = thePayment.getClientPhone();
        double amount = thePayment.getTransactionAmount();
        double amountAED = thePayment.getTransactionAmount() * 3.67;
        String photo = thePayment.getPhoto();
        String remark = thePayment.getRemark();
        String signature = thePayment.getSignature();
        String signatureAgent = thePayment.getSignatureAgent();
        String username = thePayment.getUser().getName();

        ByteArrayOutputStream reportStream = generatePaymentReportClient(ref, datePayment, partnerCode, partnerName, partnerPhone, clientName, clientAddress, clientIdCard, clientPhone, amount, amountAED, photo, remark, country,currencyPayment, signature, signatureAgent, username);
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ref + "-" +clientName+".pdf");
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    public ByteArrayOutputStream generateRechargeReport(String ref, Date date, String partnerName, String partnerPhone, String stockName, double amount, double amountAED, String username) throws IOException, JRException {
//        File file = ResourceUtils.getFile("classpath:invoicePartner.jrxml");
        Locale locale = new Locale("en", "US");
//        Resource resource = new ClassPathResource("payment-receipt.jrxml");

        // Obtenir un objet File à partir de la ressource
//        File file = resource.getFile();


        File file = ResourceUtils.getFile("/opt/tomcat/webapps/LamexPartnerTracing/WEB-INF/classes/invoicePartner.jrxml");
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("ref", ref);
        parameters.put("dateRecharge", date);
        parameters.put("partnerName", partnerName);
        parameters.put("partnerPhone", partnerPhone);
        parameters.put("stockName", stockName);
        parameters.put("amountRecharge", amount);
        parameters.put("amountRechargeAED", amountAED);
        parameters.put("username", username);
        parameters.put( JRParameter.REPORT_LOCALE, locale );

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    @GetMapping("/rechargeReportPdf")
    public ResponseEntity rechargeReport(@RequestParam("id") UUID theId, Model theModel) throws JRException, IOException{
        Movement theMovement = movementService.findMovementById(theId);
        String ref = theMovement.getReferenceTransaction();
        Date datePayment = theMovement.getTransactionDate();
        String partnerName = theMovement.getStock().getPartner().getPartnerName();
        String partnerPhone = theMovement.getStock().getPartner().getPartnerPhone();
        String stockName = theMovement.getStock().getStockName();
        double amount = theMovement.getTransactionAmount();
        double amountAED = theMovement.getTransactionAmount() * 3.67;
        String username = theMovement.getUser().getName();

        ByteArrayOutputStream reportStream = generateRechargeReport(ref, datePayment, partnerName, partnerPhone, stockName, amount, amountAED, username);
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ref + "-" +partnerName+".pdf");
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    public ByteArrayOutputStream mouvementReport(UUID theId) throws IOException, JRException {
//        File file = ResourceUtils.getFile("classpath:mouvement.jrxml");
        List<Transaction> transactions = transactionService.findTransactionsByStockId(theId);
        Stock stock = stockService.findStockById(theId);
        Locale locale = new Locale("en", "US");
        Double creditSum = stockService.creditSum(theId);
        Double debitSum = stockService.debitSum(theId);
        Double rechargeSum = stockService.rechargeSum(theId);
//        Resource resource = new ClassPathResource("payment-receipt.jrxml");

        // Obtenir un objet File à partir de la ressource
//        File file = resource.getFile();


        File file = ResourceUtils.getFile("/opt/tomcat/webapps/LamexPartnerTracing/WEB-INF/classes/mouvement.jrxml");
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put( JRParameter.REPORT_LOCALE, locale );
        parameters.put("creditSum", creditSum);
        parameters.put("debitSum", debitSum);
        parameters.put("rechargeSum", rechargeSum);
        parameters.put("stockName", stock.getStockName());

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    @GetMapping("/mouvementReportPdf")
    public ResponseEntity mouvementReport(@RequestParam("id") UUID theId, Model theModel) throws JRException, IOException{

        ByteArrayOutputStream reportStream = mouvementReport(theId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }


}
