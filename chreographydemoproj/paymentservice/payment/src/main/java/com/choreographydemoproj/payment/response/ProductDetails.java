package com.choreographydemoproj.payment.response;

public class ProductDetails {

    public ProductDetails() {
    }


    private int productId;
    private String productName;
    private int productPrice;
    private boolean productAvailable;
    private String categoryType;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductAvailable() {
        return productAvailable;
    }

    public void setProductAvailable(boolean productAvailable) {
        this.productAvailable = productAvailable;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productAvailable=" + productAvailable +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }

    public ProductDetails(int productId, String productName, int productPrice, boolean productAvailable, String categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAvailable = productAvailable;
        this.categoryType = categoryType;
    }
}
