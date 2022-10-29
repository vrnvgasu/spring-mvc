package ru.springmvc.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springmvc.models.Item;
import ru.springmvc.models.Person;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer > {

	List<Item> findByOwner(Person person);

	List<Item> findByItemName(String name);

}
