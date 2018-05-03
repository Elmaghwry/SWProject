package com.sweproject.swproject.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Collaborator {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private int IdOwner =0;
    private int StoreId =0;
    private int Type = 0;

    public Collaborator() {
    }

    public Collaborator(int idOwner, int storeId, int type) {
        IdOwner = idOwner;
        StoreId = storeId;
        Type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdOwner() {
        return IdOwner;
    }

    public void setIdOwner(int idOwner) {
        IdOwner = idOwner;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
