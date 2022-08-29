package ru.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springmvc.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
  // бин jdbcTemplate вместо JDBC API
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    // PersonMapper - RowMapper: говорит JdbcTemplate сприга, на какую модель
    // и как накладывать поля из базы данных
//    return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());

    // если все колонки модели и поля в БД совпадают, то можно брать
    // дефолтный RowMapper из спринга - BeanPropertyRowMapper
    return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
  }

  public Person show(int id) {
    // в jdbcTemplate запрос всегда подготовленный
    // надо передать массив объектов со значения
    return jdbcTemplate.query(
        "SELECT * FROM person WHERE id=?",
        new Object[]{id},
//        new PersonMapper()
        new BeanPropertyRowMapper<>(Person.class)
    )
        // получили из запроса коллекцию Person
        // вернули первый элемент или null
        .stream().findAny().orElse(null);
  }

  public void save(Person person) {
    // update ожидает в качестве параметров в sql - @Nullable Object... args
    jdbcTemplate.update("INSERT INTO person VALUES(1, ?, ?, ?)",
      person.getName(), person.getAge(), person.getEmail());
  }

  public void update(int id, Person updatedPerson) {
    jdbcTemplate.update("UPDATE person set name=?, age=?, email=? WHERE id=?",
        updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
  }
}
