package ru.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    Person person = null;

    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT * FROM person WHERE id=?");
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      resultSet.next(); // один раз сдвинем указатель, чтобы взять первую запись
      person = new Person();
      person.setId(resultSet.getInt("id"));
      person.setName(resultSet.getString("name"));
      person.setEmail(resultSet.getString("email"));
      person.setAge(resultSet.getInt("age"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return person;

  }

  public void save(Person person) {
    try {
      // подготавливает запрос и предотвращает sql инъекции
      // на большим данных работает быстрее обычного Statement
      PreparedStatement preparedStatement =
          connection.prepareStatement("INSERT INTO person VALUES(1, ?, ?, ?");
      preparedStatement.setString(1, person.getName());
      preparedStatement.setInt(2, person.getAge());
      preparedStatement.setString(2, person.getEmail());

      // executeUpdate не возвращает результат
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void update(int id, Person updatedPerson) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("UPDATE person set name=?, age=?, email=? WHERE id=?");
      preparedStatement.setString(1, updatedPerson.getName());
      preparedStatement.setInt(2, updatedPerson.getAge());
      preparedStatement.setString(3, updatedPerson.getName());
      preparedStatement.setInt(4, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(int id) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("DELETE FROM person WHERE id=?");
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
