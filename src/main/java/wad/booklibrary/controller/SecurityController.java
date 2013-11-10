/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.controller;

import wad.booklibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.booklibrary.domain.User;

@Controller
public class SecurityController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute("user") User user) {
        User test = new User();
        test = userService.getUser(user.getName());
        if (test != null || "".equals(user.getPassword())) {
            model.addAttribute("user",new User());
            return "register";
        }

        userService.addUser(user.getName(), user.getPassword());
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginView() {
        return "login";
    }

}
