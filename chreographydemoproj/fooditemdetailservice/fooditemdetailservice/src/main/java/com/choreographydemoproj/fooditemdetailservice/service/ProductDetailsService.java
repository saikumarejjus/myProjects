package com.choreographydemoproj.fooditemdetailservice.service;

import com.choreographydemoproj.fooditemdetailservice.entity.ProductDetails;
import com.choreographydemoproj.fooditemdetailservice.repository.ProductDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailsService {

    @Autowired
    private  ProductDetailsRepo productDetailsRepo;

    public ProductDetails addProducts(ProductDetails productDetails)
    {
       return productDetailsRepo.save(productDetails);
    }


     public List<ProductDetails> getAllProductDetails()
    {
       return productDetailsRepo.findAll();
    }

     public Optional<ProductDetails> getProductDetails(int productId)
    {
       return productDetailsRepo.findById(productId);

    }

     public String deleteProductDetails( int productId)
    {
        productDetailsRepo.deleteById(productId);
        return "product is deleted";
    }

     public ProductDetails updateProductDetails(ProductDetails productDetails)
    {
       return productDetailsRepo.save(productDetails);
    }

     public Optional<ProductDetails> updateProductAvailability(int productId, boolean status)
    {
         productDetailsRepo.findById(productId).ifPresent( product -> {
            product.setProductAvailable(status);
            productDetailsRepo.save(product);
        });
         return productDetailsRepo.findById(productId);
    }


}


