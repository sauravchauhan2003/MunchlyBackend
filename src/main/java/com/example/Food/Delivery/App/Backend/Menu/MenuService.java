package com.example.Food.Delivery.App.Backend.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    public List<MenuItem> getMenuByTimeAndType(MenuTime time, FoodType type) {
        return menuItemRepository.findByTimeSlotAndFoodType(time, type);
    }
    public void addMenuItem(MenuItem menuItem){
        menuItemRepository.save(menuItem);
    }
}
