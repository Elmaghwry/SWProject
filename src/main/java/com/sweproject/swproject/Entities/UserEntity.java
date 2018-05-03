package com.sweproject.swproject.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String Name = "" ;
    private String Email = "" ;
    private String Password = "" ;
    private String Gender = "" ;
    private String Type = "" ;
    private int numOfBuy = 0 ;
    private int collaborators = 0 ;

    public UserEntity() {
        Name="";
        Email="";
        Password="";
        Gender="";
        Type = "" ;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public UserEntity(String name, String email, String password, String gender, String type) {
        Name = name;
        Email = email;

        Password = password;
        Gender = gender;
        Type = type ;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getNumOfBuy() {
        return numOfBuy;
    }
    public void setNumOfBuy(int numOfBuy) {
        this.numOfBuy = numOfBuy;
    }

    public int getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }
}
