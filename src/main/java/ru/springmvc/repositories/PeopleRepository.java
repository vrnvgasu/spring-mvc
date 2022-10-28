package ru.springmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springmvc.models.Person;

// Person - сущность
// Integer - тип первичного ключа
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
