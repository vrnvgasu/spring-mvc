package ru.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Component;
import ru.springmvc.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
  private static int PEOPLE_COUNT;

  // конечно, такие данные надо хранить в конфигах
  private static String URL = "jdbc:postgresql://localhost:5490/spring_mvc";
  private static String USERNAME = "user";
  private static String PASSWORD = "user";

  // объекта для соединения с БД
  private static Connection connection;

  // сразу же проинициализируем connection
  static {
    try {
      // с помощью рефлексии загрузили класс драйвера
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      // Проблема работы с JDBC API - любая ошибка кидает SQLException
      // из-за этого сложно понять, что конкретно не так
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Person> index() {
    List<Person> people = new ArrayList<>();

    try {
      // Выполняет запрос к БД
      Statement statement = connection.createStatement();
      String sql = "SELECT * FROM person";
      // ResultSet - инкапсулирует результат запроса
      ResultSet resultSet = statement.executeQuery(sql);

      while (resultSet.next()) {
         Person person = new Person();
         person.setId(resultSet.getInt("id")); // указываем на данные из колонки id
         person.setName(resultSet.getString("name"));
         person.setEmail(resultSet.getString("email"));
         person.setAge(resultSet.getInt("age"));

         people.add(person);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return people;
  }

  public Person show(int id) {
//     return people.stream()
//             .filter(person -> person.getId() == id)
//             .findAny().orElse(null);
    return null;

  }

  public void save(Person person) {
    try {
      Statement statement = connection.createStatement();
      // такой подход может привести к sql инъекциям
      String sql = "INSERT INTO person VALUES( " +
          1 + ",'" + person.getName() + "'," + person.getAge() +
          ",'" + person.getEmail() + "')";
      // executeUpdate ничего не возвращает
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void update(int id, Person updatedPerson) {
//    Person personToBeUpdated = show(id);
//    personToBeUpdated.setName(updatedPerson.getName());
//    personToBeUpdated.setAge(updatedPerson.getAge());
//    personToBeUpdated.setEmail(updatedPerson.getEmail() );
  }

  public void delete(int id) {
//    people.removeIf(person -> person.getId() == id);
  }
}
