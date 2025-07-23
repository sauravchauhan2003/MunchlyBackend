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

    // Getters and Setters
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public MenuTime getMenuTime() {
        return menuTime;
    }

    public void setMenuTime(MenuTime menuTime) {
        this.menuTime = menuTime;
    }
}
