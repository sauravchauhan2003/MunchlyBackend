package com.example.Food.Delivery.App.Backend.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long>{
    List<MenuItem> findByTimeSlotAndFoodType(MenuTime timeSlot, FoodType foodType);
}
