package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

 @RequestMapping("/")
 public String hello(Model model) {
	 model.addAttribute("hello", "Hello,World!");
     return "hello";
 }
}