package ru.springmvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springmvc.dao.PersonDAO;
import ru.springmvc.models.Person;

// для каждой сущности свой валидатор
@Component // делаем компонентом, чтобы внедрять в контроллер
public class PersonValidator implements Validator {
  private final PersonDAO personDAO;

  @Autowired
  public PersonValidator(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    // валидатор работает только для класса Person
    return Person.class.equals(clazz);
  }

  // сама логика валидации
  // errors - хранит ошибки
  @Override
  public void validate(Object target, Errors errors) {
    // уверенны, что можем привести к Person, т.к. задали это в supports()
    Person person = (Person) target;

//    if (personDAO.show(person.getEmail()) != null) {
    // в новых версиях лучше получать объекты Optional
    if (personDAO.show(person.getEmail()).isPresent()) {
      errors.rejectValue("email", "", "This email is already taken");
    }
  }

}
