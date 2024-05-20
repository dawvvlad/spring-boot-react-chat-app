package com.vlad.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @GetMapping ("/home")
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/user/**")
    public String user() {
        return "forward:/index.html";
    }

}
