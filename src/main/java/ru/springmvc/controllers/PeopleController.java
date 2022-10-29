package ru.springmvc.controllers;

//import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springmvc.models.Person;

import javax.validation.Valid;
import ru.springmvc.services.ItemService;
import ru.springmvc.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {

  // работаем с БД через Hibernate
//  private final PersonDAO personDAO;
//  private final PersonValidator personValidator;

  // работаем с БД через JPA
  private final PeopleService peopleService;
  private final ItemService itemService;

  // DI репозитория DAO сработает внутри контроллера в конструкторе даже
  // без @Autowired
  public PeopleController(PeopleService peopleService, ItemService itemService) {
    this.peopleService = peopleService;
    this.itemService = itemService;
  }

  @GetMapping()
  // Делаем DI модели
  public String index(Model model) {
    model.addAttribute("people", peopleService.findAll());

    peopleService.test();

    return "people/index";
  }

  // ловим запросы /people/{id}
  @GetMapping("/{id}")
  public String show (@PathVariable("id") int id, Model model) {
    model.addAttribute("person", peopleService.findOne(id));
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
  // ---------------------
  // @Valid - проверяем на валидацию поля из Person (у которых есть соответсвующие аннотации типа Min и тд)
  // BindingResult - сюда попадают ошибки из @Valid
  public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
    // @Valid - уже сложил часть ошибок в bindingResult
    // personValidator.validate еще добавил туда ошибок
//    personValidator.validate(person, bindingResult);

    if (bindingResult.hasErrors()) {
      // вернули форму с созданием
      // в ней уже будут ошибки из @Valid Person
      return "people/new";
    }

    peopleService.save(person);
    return "redirect:/people";  // делаем редирект на index
  }

  @GetMapping("/{id}/edit")
  public String create(Model model, @PathVariable("id") int id) {
    model.addAttribute("person", peopleService.findOne(id));
    return "people/edit";
  }

  @PatchMapping("/{id}")
  // @ModelAttribute подставит нужные поля из формы в person
  public String update(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult,
                       @PathVariable("id") int id) {
    // @Valid - уже сложил часть ошибок в bindingResult
    // personValidator.validate еще добавил туда ошибок
//    personValidator.validate(person, bindingResult);

    if (bindingResult.hasErrors()) {
      return "people/edit";
    }

    peopleService.update(id, person);
    return "redirect:/people";  // делаем редирект на index
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    peopleService.delete(id);
    return "redirect:/people";  // делаем редирект на index
  }
}
