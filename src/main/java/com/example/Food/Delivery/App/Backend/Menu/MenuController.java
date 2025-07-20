package com.example.Food.Delivery.App.Backend.Menu;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    private final Path imageDir = Paths.get("uploads/menu-images");

    @PostConstruct
    public void initImageDirectory() {
        try {
            if (!Files.exists(imageDir)) {
                Files.createDirectories(imageDir);
                System.out.println("Image directory created at: " + imageDir.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create image directory", e);
        }
    }

    @PostMapping("/menu/{id}/upload-image")
    public ResponseEntity<String> uploadMenuImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is missing");
        }

        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = id + "." + extension;
            Path filePath = imageDir.resolve(fileName);
            Files.write(filePath, file.getBytes());
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
        }
    }

    @GetMapping("/menu/{id}/image")
    public ResponseEntity<byte[]> getMenuImage(@PathVariable Long id) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(imageDir, id + ".*")) {
            for (Path path : stream) {
                byte[] imageBytes = Files.readAllBytes(path);
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path.getFileName() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(imageBytes);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
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
