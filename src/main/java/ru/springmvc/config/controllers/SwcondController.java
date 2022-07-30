package ru.springmvc.config.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwcondController {
  @GetMapping("/exit")
  public String exit() {
    return "second/exit";
  }
}
