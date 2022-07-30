package ru.springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first") // добавили перехват всех запросов на /first
public class FirstController {
  @GetMapping("/hello")
  // HttpServletRequest - автоматом внедряет
//  public String helloPage(HttpServletRequest request) {
  public String helloPage(
          @RequestParam(value = "name", required = false) String name,
          @RequestParam(value = "surname", required = false) String surname,
          Model model // автоматом делает DI. Модель передает в представление
          ) {
//    String name = request.getParameter("name");
//    String surname = request.getParameter("surname");

    model.addAttribute( "message", "Hello, " + name + " " + surname);

    // хорошая практика - класть представления в каталог  контроллера
    return "first/hello";
  }

  @GetMapping("/goodbye")
  public String goodByePage() {
    return "first/goodbye";
  }

  @GetMapping("/calculator")
  public String getCalculator(
          @RequestParam("a") int a,
          @RequestParam("b") int b,
          @RequestParam("action") String action,
          Model model
  ) throws HttpRequestMethodNotSupportedException {
    double result;
    switch (action) {
      case "multiplication":
        result = a * b;
        break;
      case "addition":
        result = a + b;
        break;
      case "subtraction":
        result = a - b;
        break;
      case "division":
        result = a * 1D / b;
        break;
      default:
        throw new HttpRequestMethodNotSupportedException("unknown operation");
    }

    String equation = a + " " + action + " " + b;

    model.addAttribute("result", Math.round(result * 100) / 100.);
    model.addAttribute("equation",  equation);

    return "first/calculator";
  }
}
