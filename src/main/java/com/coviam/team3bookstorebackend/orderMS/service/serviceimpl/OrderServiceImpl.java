package com.coviam.team3bookstorebackend.orderMS.service.serviceimpl;

import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.repositery.OrderRepositery;
import com.coviam.team3bookstorebackend.orderMS.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepositery orderRepositery;

    @Override
    public Order save(Order order) {
        return orderRepositery.save(order);
    }

    @Override
    public Optional<Order> getOrder(String order_id) {
        return orderRepositery.findById(order_id);
    }



    public void sendmail(String order_id) throws AddressException, MessagingException, IOException {

        Order order=orderRepositery.findById(order_id).get();
        System.out.println(order.getOrderId());
        //order.setOrderId(order_id);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pritesh.radadiya@coviam.com", "azcxhncltpvwfpoe\n\n");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("pritesh.radadiya@coviam.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("priteshradadiya01@gmail.com"));
        msg.setSubject("Your Order Details");
        msg.setContent("Order Summary", "text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent("Your Order is confirmed ..."+order, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();
        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("email sent successfully !");
    }
}
