package ru.springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springmvc.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PeopleController {

  private final PersonDAO personDAO;

  // DI репозитория DAO сработает внутри контроллера в конструкторе даже
  // без @Autowired
  public PeopleController(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  @GetMapping()
  // Делаем DI модели
  public String index(Model model) {
    model.addAttribute("people", personDAO.index());
    System.out.println(personDAO.index());
    return "people/index";
  }

  // ловим запросы /people/{id}
  @GetMapping("/{id}")
  public String show (@PathVariable("id") int id, Model model) {
    model.addAttribute("person", personDAO.show(id));
    return "people/show";
  }
}
