package ru.springmvc.models;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  // зависимости из hibernate-validator
  // правила валидации будут применятся на форме, т.к. мы передаем через Model объект Person во View
  @NotEmpty(message = "Name should not be empty")
  @Size(min = 2, max = 30, message = "Name between 2 and 20")
  @Column(name = "name")
  private String name;

  @Min(value = 1, message = "Age >= 1 ")
  @Column(name = "age")
  private int age;

  @NotEmpty(message = "Email should not be empty")
  @Email // парсит на email регуляркой
  @Column(name = "email")
  private String email;

  // валидный адрес: Страна, Город, индекс(6 цифр)
  @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
      message = "Your address should be in this format: Country, City, postal code (6 digits)")
  @Column(name = "address")
  private String address;

  @OneToMany(mappedBy = "owner")
  private List<Item> items;

  @Column(name = "date_of_birth")
  @Temporal(TemporalType.DATE) // hibernate правильно конвертирует дату для БД
  @DateTimeFormat(pattern = "dd/MM/yyyy") // парсим дату из формы в формате дд/мм/гггг
  private Date dateOfBirth;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  public Person(String name, int age, String email, String address) {
    this.name = name;
    this.age = age;
    this.email = email;
    this.address = address;
  }

  public Person() {

  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
