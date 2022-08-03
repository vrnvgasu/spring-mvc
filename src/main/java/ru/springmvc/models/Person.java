package ru.springmvc.models;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
  private int id;

  // зависимости из hibernate-validator
  // правила валидации будут применятся на форме, т.к. мы передаем через Model объект Person во View
  @NotEmpty(message = "Name should not be empty")
  @Size(min = 2, max = 30, message = "Name between 2 and 20")
  private String name;
  @Min(value = 0, message = "Age >= 0 ")
  private int age;
  @NotEmpty(message = "Email should not be empty")
  @Email // парсит на email регуляркой
  private String email;

  public Person(int id, String name, int age, String email) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
  }

  public Person() {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
