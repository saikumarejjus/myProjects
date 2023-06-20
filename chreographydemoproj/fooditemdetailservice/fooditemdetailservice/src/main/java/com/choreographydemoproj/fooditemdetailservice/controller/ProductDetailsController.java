package com.choreographydemoproj.fooditemdetailservice.controller;

import com.choreographydemoproj.fooditemdetailservice.entity.ProductDetails;
import com.choreographydemoproj.fooditemdetailservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @Value("${server.port}")
    private int portNUmber;

    @PostMapping("/addProducts")
    public ProductDetails addProducts(@RequestBody ProductDetails productDetails)
    {
        return productDetailsService.addProducts(productDetails);
    }
    @GetMapping("/getAllProducts")
    public List<ProductDetails> getAllProductDetails()
    {
       return productDetailsService.getAllProductDetails();
    }

    @GetMapping("/getProductsById/{id}")
    public Optional<ProductDetails> getProductDetails(@PathVariable("id") int productId)
    {
        System.out.println("server port is--------------------"+portNUmber);
        return productDetailsService.getProductDetails(productId);
    }

    @DeleteMapping("/deleteProductsById/{id}")
    public String deleteProductDetails(@PathVariable("id") int productId)
    {
      return   productDetailsService.deleteProductDetails(productId);
    }

    @PutMapping("/updateProductsById")
    public ProductDetails updateProductDetails(@RequestBody ProductDetails productDetails )
    {
       return productDetailsService.updateProductDetails(productDetails);
    }

    @PutMapping("/updateAvailability/{productId}/{status}")
    public Optional<ProductDetails> updateProductAvailability(@PathVariable("productId") int productId, @PathVariable("status") boolean status)
    {
        return productDetailsService.updateProductAvailability(productId,status);
    }



}
