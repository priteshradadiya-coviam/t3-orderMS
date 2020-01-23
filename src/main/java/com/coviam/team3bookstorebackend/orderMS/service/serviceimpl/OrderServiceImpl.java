package com.coviam.team3bookstorebackend.orderMS.service.serviceimpl;

import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;
import com.coviam.team3bookstorebackend.orderMS.repositery.OrderDetailsRepositery;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepositery orderRepositery;


    @Autowired
    OrderDetailsRepositery orderDetailsRepositery;

    @Override
    public OrderDetails saveDetails(OrderDetails orderDetails) {
        return orderDetailsRepositery.save(orderDetails);
    }

    @Override
    public Order save(Order order) {
        return orderRepositery.save(order);
    }

    @Override
    public Optional<Order> getOrder(String order_id) {
        return orderRepositery.findById(order_id);
    }

    @Override
    public void sendEmail(OrderDTO ordercreated) throws AddressException, MessagingException, IOException
    {



        Order order=orderRepositery.findById(ordercreated.getOrderId()).get();

        ArrayList<OrderDetails> orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsRepositery.findAll();




        orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsArrayList.stream().filter(orderDetails ->
            orderDetails.getOrderId().equals(ordercreated.getOrderId())).collect(Collectors.toList());

        System.out.println(order.getOrderId());
        //order.setOrderId(order_id);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("book.adda007@gmail.com", "ajgoyicabhtgqdkv\n\n"); }});
                                                                //pritesh.radadiya@coviam.com//azcxhncltpvwfpoe
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("book.adda007@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ordercreated.getCustomerEmail()));//"priteshradadiya01@gmail.com"
        msg.setSubject("Your Order is confirmed ...");
        msg.setContent("Order Summary", "text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        StringBuilder orderinfo= new StringBuilder();
        for (OrderDetails orderDetails:orderDetailsArrayList) {
            orderinfo.append(orderDetails.toString());
            orderinfo.append("\n");


            //TODO using StringBuilder
                        // TODO store all details in one string builder and then store it into a messagewBodypart.setcontent


        }
        messageBodyPart.setContent("____"+orderinfo, "text/html");
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
