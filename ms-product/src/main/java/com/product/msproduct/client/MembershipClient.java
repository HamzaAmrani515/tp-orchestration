package com.product.msproduct.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MembershipClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean memberExists(Long memberId) {
        try {
            restTemplate.getForObject(
                    "http://localhost:8081/api/v1/members/" + memberId,
                    Object.class
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
