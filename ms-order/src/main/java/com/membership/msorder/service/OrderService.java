package com.membership.msorder.service;

import com.membership.msorder.client.MembershipClient;
import com.membership.msorder.client.ProductClient;
import com.membership.msorder.domain.Order;
import com.membership.msorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final MembershipClient membershipClient;
    private final ProductClient productClient;

    public Order create(Order order) {

        if (!membershipClient.memberExists(order.getMemberId())) {
            throw new RuntimeException("Member not found");
        }

        if (!productClient.productExists(order.getProductId())) {
            throw new RuntimeException("Product not found");
        }

        order.setId(null);
        order.setStatus("CREATED");

        return repository.save(order);
    }
}
