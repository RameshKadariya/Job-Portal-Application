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

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}