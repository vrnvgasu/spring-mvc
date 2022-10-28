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
		return session.createQuery("select p from Person p", Person.class)
				.getResultList();
	}

	// Optional - обертка вокруг объектов, которые могут существовать или нет (null)
	@Transactional(readOnly = true)
	public Optional<Person> show(String email) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("select p from Person p where p.email=:email", Person.class)
				.setParameter("email", email)
				.getResultList()
				.stream().findAny();
	}

	@Transactional(readOnly = true)
	public Person show(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Person.class, id);
	}

	@Transactional
	public void save(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.save(person);
	}

	@Transactional
	public void update(int id, Person updatedPerson) {
		Session session = sessionFactory.getCurrentSession();
		Person personToBeUpdated = session.get(Person.class, id);

		personToBeUpdated.setName(updatedPerson.getName());
		personToBeUpdated.setAge(updatedPerson.getAge());
		personToBeUpdated.setEmail(updatedPerson.getEmail());
		personToBeUpdated.setAddress(updatedPerson.getAddress());
	}

	@Transactional
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(session.get(Person.class, id));
	}

}
