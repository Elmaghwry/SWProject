package com.sweproject.swproject.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Historical {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    String Name = "" ;
    double Price = 0.0;
    String Category = "" ;
    int num_of_views = 0 ;
    String Brand = "" ;
    int Quantity = 0;
    int numOfBuy =0;
    String Type = "" ;
    int idProduct = 0;
    String StoreName = "" ;
    int StoreId = 0;
    public Historical() {
    }

    public Historical(String name, double price, String category, int num_of_views, String brand, int quantity, int numOfBuy, String type) {
        Name = name;
        Price = price;
        Category = category;
        this.num_of_views = num_of_views;
        Brand = brand;
        Quantity = quantity;
        this.numOfBuy = numOfBuy;
        Type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getNum_of_views() {
        return num_of_views;
    }

    public void setNum_of_views(int num_of_views) {
        this.num_of_views = num_of_views;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getNumOfBuy() {
        return numOfBuy;
    }

    public void setNumOfBuy(int numOfBuy) {
        this.numOfBuy = numOfBuy;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }
}
