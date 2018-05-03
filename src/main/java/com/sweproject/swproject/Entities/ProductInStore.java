package com.sweproject.swproject.Entities;

import javax.persistence.*;

@Entity
public class ProductInStore {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    int IdProduct = -1 ;
    int IdStore = -1 ;
    String NameProduct = "" ;
    String NameStore = "" ;

    public ProductInStore() {
    }

    public ProductInStore(int idProduct, int idStore, String nameProduct, String nameStore) {
        IdProduct = idProduct;
        IdStore = idStore;
        NameProduct = nameProduct;
        NameStore = nameStore;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public int getIdStore() {
        return IdStore;
    }

    public void setIdStore(int idStore) {
        IdStore = idStore;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getNameStore() {
        return NameStore;
    }

    public void setNameStore(String nameStore) {
        NameStore = nameStore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
