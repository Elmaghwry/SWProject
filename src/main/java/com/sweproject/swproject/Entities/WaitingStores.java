package com.sweproject.swproject.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class WaitingStores {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    String Name = "";
    String Owner = "" ;
    int acceptable = 0 ;
    public WaitingStores() {
    }

    public WaitingStores(String name, String owner) {
        Name = name;
        Owner = owner;
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

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public int isAcceptable() {
        return acceptable;
    }

    public void setAcceptable(int acceptable) {
        this.acceptable = acceptable;
    }
}
