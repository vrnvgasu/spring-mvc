package ru.springmvc.dao;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.springmvc.models.Person;
import java.util.List;

@Component
public class PersonDAO {

	private final SessionFactory sessionFactory;

	public PersonDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// автоматом открывает/закрывает транзакцию
	@Transactional(readOnly = true)
	public List<Person> index() {
		Session session = sessionFactory.getCurrentSession();
		List<Person> people = session.createQuery("select p from Person p", Person.class)
				.getResultList();
		return people;
	}

	// Optional - обертка вокруг объектов, которые могут существовать или нет (null)
	public Optional<Person> show(String email) {
		return null;
	}


	public Person show(int id) {
		return null;
	}

	public void save(Person person) {

	}

	public void update(int id, Person updatedPerson) {

	}

	public void delete(int id) {

	}

}
