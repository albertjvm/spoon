package com.albertjvm.controller;

import com.albertjvm.data.UserMapper;
import com.albertjvm.model.Session;
import com.albertjvm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("session")
@RequestMapping(value = "login")
public class LoginController {

    private final UserMapper userMapper;

    @Autowired private Session session;

    @Autowired
    public LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @ModelAttribute("session")
    public Session addToScope() {
        Session session = new Session();
        return session;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity doLogin(HttpServletRequest request,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "password") String password) {

        ResponseEntity response;
        User user = userMapper.getUserByEmail(email);
        HttpSession httpSession = request.getSession();

        if (user == null) {
            response = new ResponseEntity<>("No user with specified email exists - [" + email + "]", HttpStatus.BAD_REQUEST);
        } else if (!passwordsMatch(user.getPassword(), password)) {
            response = new ResponseEntity<>("Password does not match", HttpStatus.UNAUTHORIZED);
        } else {
            session.setUserId(user.getId());
            httpSession.setAttribute("email", user.getEmail());
            response = new ResponseEntity<>(user, HttpStatus.OK);
        }

        return response;
    }

    private boolean passwordsMatch(String userPassword, String passwordAttempt) {
        return userPassword.equals(passwordAttempt);
    }
}
