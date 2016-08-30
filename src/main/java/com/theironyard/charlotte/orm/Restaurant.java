package com.theironyard.charlotte.orm;

import javax.persistence.*;

/**
 * Created by charlesrath on 8/29/16.
 */
@Entity
@Table (name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Integer zipcode;

    @Column(nullable = false)
    private String cuisine;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer price;

    public Restaurant() {}
    public Restaurant(String name, String address, String city, String state, Integer zipcode, String cuisine, String type, Integer price) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.cuisine = cuisine;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void update(Restaurant r) {
        this.setAddress(r.getAddress());
        this.setCity(r.getCity());
        this.setState(r.getState());
        this.setZipcode(r.getZipcode());
        this.setName(r.getName());
        this.setCuisine(r.getCuisine());
        this.setType(r.getType());
        this.setPrice(r.getPrice());
    }
}
