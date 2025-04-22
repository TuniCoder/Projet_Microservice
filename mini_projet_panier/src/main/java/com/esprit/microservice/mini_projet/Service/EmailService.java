package com.esprit.microservice.mini_projet.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendOrderConfirmationEmail(String to, String orderId, String total) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Confirmation de Commande - " + orderId);

        // Chemin vers le logo
        String logoPath = "./uploads/logo.png";
        FileSystemResource logo = new FileSystemResource(new File(logoPath));

        // Ajouter le HTML avec la balise image liée à l'ID cid:logo
        String htmlMsg = "<html><body>"
                + "<img src='cid:logo' style='width:150px; height:auto;'/><br>"
                + "<h3>Merci pour votre commande</h3>"
                + "<p>Votre commande <strong>" + orderId + "</strong> a été reçue avec succès.</p>"
                + "<p>Total : " + total + "</p>"
                + "<p>Nous vous enverrons les informations de suivi lorsque la commande sera expédiée.</p>"
                + "<p>Merci de faire vos achats avec nous!</p>"
                + "</body></html>";

        helper.setText(htmlMsg, true); // true = HTML
        helper.addInline("logo", logo); // "logo" correspond à cid:logo

        emailSender.send(message);
    }
}
