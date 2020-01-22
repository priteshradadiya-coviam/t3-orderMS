package com.coviam.team3bookstorebackend.orderMS.repositery;

import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepositery extends CrudRepository<Order,String> {
}
