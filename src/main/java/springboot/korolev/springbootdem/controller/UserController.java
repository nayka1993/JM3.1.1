package springboot.korolev.springbootdem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.korolev.springbootdem.model.Role;
import springboot.korolev.springbootdem.model.User;
import springboot.korolev.springbootdem.service.UserService;

import java.util.Collections;


@Controller
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setUserService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRoles(Collections.singleton(new Role(2L)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/user")
    public String getUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }
}
