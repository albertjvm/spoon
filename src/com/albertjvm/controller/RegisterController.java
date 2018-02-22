package com.albertjvm.controller;

import com.albertjvm.data.UserMapper;
import com.albertjvm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "register")
public class RegisterController {

    private final UserMapper userMapper;

    @Autowired
    public RegisterController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password) {

        ResponseEntity response;
        User user = userMapper.getUserByEmail(email);

        if (user != null) {
            response = new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } else {
            user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userMapper.createUser(user);

            response = new ResponseEntity<>(user, HttpStatus.OK);
        }

        return response;
    }
}
