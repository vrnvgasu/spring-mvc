package ru.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("/hello-world")
  public String sayHello() {
    // возвращаем представление src/main/webapp/WEB-INF/views/hello_world.html
    return "hello_world";
  }

}
