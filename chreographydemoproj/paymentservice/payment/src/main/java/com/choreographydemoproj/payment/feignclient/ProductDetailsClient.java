package com.choreographydemoproj.payment.feignclient;

import com.choreographydemoproj.payment.response.ProductDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value ="FOOD-ITEMS-SERVER", path = "/products")
public interface ProductDetailsClient {

    @GetMapping("/getProductsById/{id}")
    public Optional<ProductDetails> getProductDetails(@PathVariable("id") int productId);
}
