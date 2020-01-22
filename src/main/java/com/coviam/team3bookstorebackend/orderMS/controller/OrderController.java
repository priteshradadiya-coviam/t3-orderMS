package com.coviam.team3bookstorebackend.orderMS.controller;

import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.service.OrderService;
import com.coviam.team3bookstorebackend.orderMS.service.serviceimpl.Email;
import com.coviam.team3bookstorebackend.orderMS.service.serviceimpl.OrderServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    OrderService orderService;


    @Autowired
    OrderController orderController;


    @PostMapping(value = "/addOrderDetails")
    private String addOrderDetails(@RequestBody OrderDTO orderDTO)
    {

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO,order);
        System.out.println(order);
        Order orderCreated = orderService.save(order);
        return orderCreated.getOrderId();

    }


    @GetMapping(value = "/getOrderDetails/{id}")
    public Order getOrderDetails(@PathVariable("id") String order_id)
    {
        Optional<Order> optionalOrder=orderService.getOrder(order_id);
        Order order=optionalOrder.get();
        order.setOrderId(order_id);
        return  order;
    }


    @PostMapping(value = "/generateEmail/{id}")
    private void sendEmail(@PathVariable("id") String order_id) throws Exception {



          orderService.sendmail(order_id);

        System.out.println("............Email");

    }



}
