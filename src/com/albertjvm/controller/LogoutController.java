package com.albertjvm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "logout")
public class LogoutController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity doLogout(HttpSession httpSession) {
        httpSession.invalidate();
        return new ResponseEntity(HttpStatus.OK);
    }
}
