package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    @Autowired
    AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String allUsersList(ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> userList = userService.getUserList();
        model.addAttribute("current", user);
        model.addAttribute("users", userList);
        return "admin_panel";
    }
    @GetMapping(value = "/adding_form")
    public String getAddingForm(ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("current", user);
        model.addAttribute("newuser", new User());
        return "add_user";
    }
    @GetMapping(value="/delete_form")
    public String getDeleteForm(@RequestParam(value="id") long id, ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> userList = userService.getUserList();
        model.addAttribute("current", user);
        model.addAttribute("users", userList);
        model.addAttribute("user", userService.getUser(id));
        return "delete_form";
    }
    @GetMapping(value="/delete")
    public String deleteUser(@ModelAttribute User user, ModelMap model) {
        userService.removeUser(user.getId());
        return allUsersList(model);
    }
    @GetMapping(value = "/add")
    public String saveUser(@ModelAttribute User user,
                           ModelMap model) {
        userService.addUser(user);
        return allUsersList(model);
    }
    @GetMapping(value = "/edit_form")
    public String redactionForm(@RequestParam(value="id") long id, ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> userList = userService.getUserList();
        model.addAttribute("current", user);
        model.addAttribute("users", userList);
        model.addAttribute("user", userService.getUser(id));
        return "edit_form";
    }
    @GetMapping(value = "/do_redact")
    public String doRedact(@ModelAttribute User user,
                           ModelMap model) {
        userService.redactUser(user.getId(), user);
        return allUsersList(model);
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        e.printStackTrace();
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
