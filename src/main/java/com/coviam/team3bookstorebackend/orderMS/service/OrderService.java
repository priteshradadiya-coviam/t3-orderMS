package com.coviam.team3bookstorebackend.orderMS.service;

import com.coviam.team3bookstorebackend.orderMS.entity.Order;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    Optional<Order> getOrder(String order_id);

    void sendmail(String order_id) throws MessagingException, IOException;

}
