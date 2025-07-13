package com.example.Food.Delivery.App.Backend.OrderManagement;

import com.example.Food.Delivery.App.Backend.Menu.FoodType;
import com.example.Food.Delivery.App.Backend.Menu.MenuTime;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {
    private String itemname;
    private double price;
    private int quantity;
    private double totalprice;
    private FoodType foodType;
    private MenuTime menuTime;
}
