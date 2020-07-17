package springboot.korolev.springbootdem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.korolev.springbootdem.model.User;
import springboot.korolev.springbootdem.service.UserService;


@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @PreAuthorize(value ="hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUser", userService.listUsers());
        return "users";
    }

    @PreAuthorize(value ="hasAuthority('ADMIN')")
    @RequestMapping(value= "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PreAuthorize(value ="hasAuthority('ADMIN')")
    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        this.userService.removeUser(id);
        return "redirect:/admin";
    }

    @PreAuthorize(value ="hasAuthority('ADMIN')")
    @RequestMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PreAuthorize(value ="hasAuthority('ADMIN')")
    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/admin";
    }



}
