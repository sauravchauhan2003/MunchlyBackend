package com.example.Food.Delivery.App.Backend.OrderManagement;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String line1;
    private String line2;
    private String city;
    private String pincode;
    private String state;
    private Double latitude;
    private Double longitude;
}
