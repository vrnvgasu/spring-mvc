package ru.springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.springmvc.dao.PersonDAO;
import ru.springmvc.models.Person;

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

  @GetMapping("/new") // форма для создания
  public String newPerson (@ModelAttribute("person") Person person) {
    // @ModelAttribute создает пустой объект Person
    // и сразу передает его во View
    // это уже не надо model.addAttribute("person", new Person());
    return "people/new";
  }

  @PostMapping()
  // @ModelAttribute собриает все поля относящиеся к модели Person
  // и подставляет их в объект (вместо того, чтобы перечислять все переменные запроса,
  // потом самим создавать объект и заполнять его этими переменными
  // и добавляем Person в Model (тут сразу передастся во View)
  public String create(@ModelAttribute("person") Person person) {
    personDAO.save(person);
    return "redirect:/people";  // делаем редирект на index
  }

  @GetMapping("/{id}/edit")
  public String create(Model model, @PathVariable("id") int id) {
    model.addAttribute("person", personDAO.show(id));
    return "people/edit";
  }

  @PatchMapping("/{id}")
  // @ModelAttribute подставит нужные поля из формы в person
  public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
    personDAO.update(id, person);
    return "redirect:/people";  // делаем редирект на index
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    personDAO.delete(id);
    return "redirect:/people";  // делаем редирект на index
  }
}
