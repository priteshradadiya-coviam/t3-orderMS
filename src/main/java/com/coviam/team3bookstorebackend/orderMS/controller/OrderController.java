package com.coviam.team3bookstorebackend.orderMS.controller;

import com.coviam.team3bookstorebackend.orderMS.dto.CheckOutDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderDetailsDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;
import com.coviam.team3bookstorebackend.orderMS.service.OrderService;
//import com.coviam.team3bookstorebackend.orderMS.service.serviceimpl.Email;
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


    @PostMapping(value = "/addOrder")
    private String addOrder(@RequestBody CheckOutDTO checkOutDTO) throws IOException, MessagingException {

        Order order = new Order();

        BeanUtils.copyProperties(checkOutDTO,order);

        System.out.println(order);

        Order orderCreated = orderService.save(order);

//        OrderDetails orderDetails=new OrderDetails();
//        BeanUtils.copyProperties(checkOutDTO,orderDetails);
//        orderDetails.setOrderId(orderCreated.getOrderId());
//
//        OrderDetails orderDetailsCreated=orderService.saveDetails(orderDetails);
//
//
//        System.out.println(orderDetails);

       // orderService.sendmail(orderCreated);
       // System.out.println("............Email");
     //   System.out.println(orderDetailsCreated);


        return orderCreated.getOrderId();

    }

    @PostMapping(value = "/addOrderDetails")
    private String addOrderDetails(@RequestBody CheckOutDTO checkOutDTO) throws IOException, MessagingException
    {
        OrderDetails orderDetails=new OrderDetails();
        BeanUtils.copyProperties(checkOutDTO,orderDetails);
        orderDetails.setOrderId(checkOutDTO.getOrderId());

        OrderDetails orderDetailsCreated=orderService.saveDetails(orderDetails);


        System.out.println(orderDetails);

        return "success";


    }



        @GetMapping(value = "/getOrderDetails/{id}")
    public Order getOrderDetails(@PathVariable("id") String order_id)
    {
        Optional<Order> optionalOrder=orderService.getOrder(order_id);
        Order order=optionalOrder.get();
        order.setOrderId(order_id);
        return  order;
    }


   @PostMapping(value = "/generateEmail")
    public void sendEmail(@RequestBody OrderDTO orderDTO) throws Exception {


       System.out.println("............Email"+orderDTO.getOrderId());


         orderService.sendEmail(orderDTO);

       System.out.println("............Email");

    }



}
