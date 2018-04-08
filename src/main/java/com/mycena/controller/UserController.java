package com.mycena.controller;

import com.mycena.domain.security.User;
import com.mycena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/User")
    public ResponseEntity createUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(username);
        System.out.println(password);
        User user = userService.createUser(username, password);
        if (user == null)
            return new ResponseEntity(HttpStatus.IM_USED);
        else
            return new ResponseEntity(user, HttpStatus.OK);
    }

}