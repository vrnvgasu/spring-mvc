package ru.springmvc.config.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/first") // добавили перехват всех запросов на /first
public class FirstController {
  @GetMapping("/hello")
  public String helloPage() {
    // хорошая практика - класть представления в каталог  контроллера
    return "first/hello";
  }

  @GetMapping("/goodbye")
  public String goodByePage() {
    return "first/goodbye";
  }
}
