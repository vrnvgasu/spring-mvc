package ru.springmvc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springmvc.models.Person;
import ru.springmvc.repositories.PeopleRepository;

@Service
// Открываем/закрываем транзакции автоматом. Пометили не методы, а целый класс
@Transactional(readOnly = true)
public class PeopleService {

	private final PeopleRepository peopleRepository;

	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	// по умолчанию @Transactional(readOnly = true), как для класса
	public List<Person> findAll() {
		return peopleRepository.findAll();
	}

	public Person findOne(int id) {
		Optional<Person> optionalPerson = peopleRepository.findById(id);
		return optionalPerson.orElse(null);
	}

	@Transactional // переопределили Transactional
	public void save(Person person) {
		person.setCreatedAt(new Date());
		peopleRepository.save(person);
	}

	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepository.save(updatedPerson);	// для существующего id сделает обновление
	}

	@Transactional
	public void delete(int id) {
		peopleRepository .deleteById(id);
	}

	public void test() {
		System.out.println("Test here. Inside HibernateTransaction");
	}

}
