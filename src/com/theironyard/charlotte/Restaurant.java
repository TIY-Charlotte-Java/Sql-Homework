package com.theironyard.charlotte;

/**
 * Created by Ben on 1/9/17.
 */
public class Restaurant {
    public int id;
    public String name;
    public String phone;
    public String type;
    public boolean isOpen;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String phone, String type, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.isOpen = isOpen;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
