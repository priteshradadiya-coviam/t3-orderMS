package com.coviam.team3bookstorebackend.orderMS.service;

import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    Optional<Order> getOrder(String order_id);

    void sendEmail(OrderDTO ordercreated) throws MessagingException, IOException;

    OrderDetails saveDetails(OrderDetails orderDetails);
}
