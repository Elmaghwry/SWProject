package com.sweproject.swproject.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductEntity {
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
    public ProductEntity() {

         Name = "" ;
         Price = 0.0;
         Category = "" ;
        num_of_views = 0;
         Brand = "" ;
        Quantity = 0;
    }

    public ProductEntity(String name, double price, String category, int numOfViews, String brand, int quantity) {
        Name = name;
        Price = price;
        Category = category;
        num_of_views = numOfViews;
        Brand = brand;
        Quantity = quantity ;
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

    public int getNumOfViews() {
        return num_of_views;
    }

    public void setNumOfViews(int numOfViews) {
        num_of_views = numOfViews;
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
}
