package ru.springmvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.springmvc.models.Person;

// говорит JdbcTemplate сприга, на какую модель
// и как накладывать поля из базы данных
public class PersonMapper implements RowMapper<Person> {

	@Override
	// ResultSet - результат из БД
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getInt("id"));
		person.setName(resultSet.getString("name"));
		person.setEmail(resultSet.getString("email"));
		person.setAge(resultSet.getInt("age"));

		return person ;
	}

}
