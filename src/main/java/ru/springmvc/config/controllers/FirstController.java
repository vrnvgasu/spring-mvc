package ru.springmvc.config.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first") // добавили перехват всех запросов на /first
public class FirstController {
  @GetMapping("/hello")
  // HttpServletRequest - автоматом внедряет
//  public String helloPage(HttpServletRequest request) {
  public String helloPage(
          @RequestParam(value = "name", required = false) String name,
          @RequestParam(value = "surname", required = false) String surname
          ) {
//    String name = request.getParameter("name");
//    String surname = request.getParameter("surname");

    System.out.println("Hello, " + name + " " + surname);

    // хорошая практика - класть представления в каталог  контроллера
    return "first/hello";
  }

  @GetMapping("/goodbye")
  public String goodByePage() {
    return "first/goodbye";
  }
}
