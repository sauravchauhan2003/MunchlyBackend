package com.example.Food.Delivery.App.Backend.Menu;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/{time}/{type}")
    public List<MenuItem> getMenuByTimeAndType(
            @PathVariable MenuTime time,
            @PathVariable FoodType type) {
        return menuService.getMenuByTimeAndType(time, type);
    }
    @PostMapping("/add-menu-item")
    public MenuItem addMenuItem(HttpServletResponse response,
                                @RequestHeader String name,
                                @RequestHeader String description,
                                @RequestHeader double price,
                                @RequestHeader String foodType,
                                @RequestHeader String timeSlot){
        if(name.isEmpty()||description.isEmpty()||foodType.isEmpty()||timeSlot.isEmpty()){
            response.setStatus(400);
            return null;
        }
        else{
            MenuItem menuItem=new MenuItem();
            menuItem.setDescription(description);
            if(foodType.equals("VEG")){
                menuItem.setFoodType(FoodType.VEG);
            }
            else{
                menuItem.setFoodType(FoodType.NON_VEG);
            }
            menuItem.setPrice(price);
            if(timeSlot.equals("BREAKFAST")){
                menuItem.setTimeSlot(MenuTime.BREAKFAST);
            } else if (timeSlot.equals("LUNCH")) {
                menuItem.setTimeSlot(MenuTime.LUNCH);
            }
            else menuItem.setTimeSlot(MenuTime.DINNER);
            menuService.addMenuItem(menuItem);
            return menuItem;
        }
    }
}
