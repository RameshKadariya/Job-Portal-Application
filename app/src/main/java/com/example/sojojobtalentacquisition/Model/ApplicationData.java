package com.example.sojojobtalentacquisition.Model;

public class ApplicationData {
    private String name;
    private String address;
    private String contact;
    private String email;
    private String id;

    public ApplicationData() {
    }

    public ApplicationData(String name, String address, String contact, String email, String id) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}