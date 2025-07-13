package com.example.Food.Delivery.App.Backend.OrderManagement;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ElementCollection
    private List<OrderItem> items;
    private String username;
    private Address address;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private boolean paid;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
