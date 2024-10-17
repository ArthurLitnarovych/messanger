package com.intellekta.messenger.controller;

import com.intellekta.messenger.entity.User;
import com.intellekta.messenger.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // View for login form
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user); // Store user in session
            return "redirect:/messages";
        }
        return "redirect:/login?error"; // Redirect back on failure
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // View for registration form
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        if (userRepository.findByUsername(username) == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password); // Make sure to hash passwords in a real app
            userRepository.save(newUser);
            return "redirect:/login";
        }
        return "redirect:/register?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session to log out
        return "redirect:/login";
    }
}
