package com.example.Food.Delivery.App.Backend.Authentication;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private MyUserRepository repository;
    @Autowired
    private JwtService jwtService;
    public String register(@RequestHeader String email,
                           @RequestHeader String username,
                           @RequestHeader String password,
                           HttpServletResponse response){
        if(email.isEmpty()||password.isEmpty()||username.isEmpty()){
            response.setStatus(400);
            return "Missing one or more parameters";
        }
        Optional<MyUser> user1=repository.findByUsername(username);
        Optional<MyUser> user2=repository.findBYEmail(email);
        if(user1.isPresent()||user2.isPresent()){
            response.setStatus(400);
            return "Username or email already in use";
        }
        MyUser myUser=new MyUser();
        myUser.setEmail(email);
        myUser.setPassword(password);
        myUser.setUsername(username);
        repository.save(myUser);
        response.setStatus(200);
        return jwtService.generateToken(myUser);
    }
}
