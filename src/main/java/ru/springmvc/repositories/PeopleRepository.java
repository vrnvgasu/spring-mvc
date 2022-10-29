package ru.springmvc.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springmvc.models.Person;

// Person - сущность
// Integer - тип первичного ключа
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

	public List<Person> findByName(String name);

	public List<Person> findByNameOrderByAge(String name);

	public List<Person> findByEmail(String email);

	public List<Person> findByNameStartingWith(String nameStart);

	public List<Person> findByNameOrEmail(String name, String email);

}
