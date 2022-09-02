package ru.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

  // Optional - обертка вокруг объектов, которые могут существовать или нет (null)
  public Optional<Person> show(String email) {
    return jdbcTemplate.query(
            "SELECT * FROM person WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
        // findAny - возвращает Optional: Optional<T> findAny();
        .stream().findAny();
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
    jdbcTemplate.update("INSERT INTO person (name, age, email, address) VALUES(?, ?, ?, ?)",
        person.getName(), person.getAge(), person.getEmail(), person.getAddress()
    );
  }

  public void update(int id, Person updatedPerson) {
    jdbcTemplate.update("UPDATE person set name=?, age=?, email=?, address=? WHERE id=?",
        updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id
    );
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
  }

  ///////////////////////////////
  ///////// Тестируем производительность пакетной вставки
  ///////////////////////////////

  public void testBatchUpdate() {
    List<Person> people = create1000People();

    long before = System.currentTimeMillis();
    // вставка через batch
    jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
      @Override
      // preparedStatement добавляет данные к запросу "INSERT INTO person VALUES(?, ?, ?, ?)"
      // i счетчик от 0 до getBatchSize()-1
      public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        preparedStatement.setInt(1, people.get(i).getId());
        preparedStatement.setString(2, people.get(i).getName());
        preparedStatement.setInt(3, people.get(i).getAge());
        preparedStatement.setString(4, people.get(i).getEmail());
      }

      @Override
      public int getBatchSize() {
        return people.size();
      }
    });
    long after = System.currentTimeMillis();

    System.out.println("Time testBatchUpdate: " + (after - before));
  }

  public void testMultipleUpdate() {
    List<Person> people = create1000People();

    long before = System.currentTimeMillis();
    people.forEach(person -> jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
        person.getId(), person.getName(), person.getAge(), person.getEmail()
    ));
    long after = System.currentTimeMillis();

    System.out.println("Time testMultipleUpdate: " + (after - before));
  }

  private List<Person> create1000People() {
    List<Person> people = new ArrayList<>();

    for (int i = 0; i < 1000; i++) {
      Person person = new Person(i, "Name" + i, 20, "test" + i + "gmail.com", "some address");
      people.add(person);
    }

    return people;
  }

}
