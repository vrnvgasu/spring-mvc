package ru.springmvc.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.springmvc.models.Item;
import ru.springmvc.models.Person;

@Component
public class PersonDAO {

	// бин для JPA вместо SessionFactory sessionFactory для Hibernate
	private final EntityManager entityManager;

	public PersonDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	public void testNPlus1() {
		// для JPA вместо Session session = sessionFactory.getCurrentSession() для Hibernate
		Session session = entityManager.unwrap(Session.class);

//		List<Person> people = session.createQuery("select p FROM Person p", Person.class)
//				.getResultList();
//
//		// N запросов
//		for (Person person: people) {
//			System.out.println("Person " + person.getName() + " has items: " + person.getItems() );
//		}

		Set<Person> people = new HashSet<>(session.createQuery("select p FROM Person p LEFT JOIN fetch p.items", Person.class)
				.getResultList());
		// без доп запросов
		for (Person person : people) {
			System.out.println("Person " + person.getName() + " has items: " + person.getItems());
		}
	}

//	private final SessionFactory sessionFactory;
//
//	public PersonDAO(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
//
//	// автоматом открывает/закрывает транзакцию
//	@Transactional(readOnly = true)
//	public List<Person> index() {
//		Session session = sessionFactory.getCurrentSession();
//		return session.createQuery("select p from Person p", Person.class)
//				.getResultList();
//	}
//
//	// Optional - обертка вокруг объектов, которые могут существовать или нет (null)
//	@Transactional(readOnly = true)
//	public Optional<Person> show(String email) {
//		Session session = sessionFactory.getCurrentSession();
//		return session.createQuery("select p from Person p where p.email=:email", Person.class)
//				.setParameter("email", email)
//				.getResultList()
//				.stream().findAny();
//	}
//
//	@Transactional(readOnly = true)
//	public Person show(int id) {
//		Session session = sessionFactory.getCurrentSession();
//		return session.get(Person.class, id);
//	}
//
//	@Transactional
//	public void save(Person person) {
//		Session session = sessionFactory.getCurrentSession();
//		session.save(person);
//	}
//
//	@Transactional
//	public void update(int id, Person updatedPerson) {
//		Session session = sessionFactory.getCurrentSession();
//		Person personToBeUpdated = session.get(Person.class, id);
//
//		personToBeUpdated.setName(updatedPerson.getName());
//		personToBeUpdated.setAge(updatedPerson.getAge());
//		personToBeUpdated.setEmail(updatedPerson.getEmail());
//		personToBeUpdated.setAddress(updatedPerson.getAddress());
//	}
//
//	@Transactional
//	public void delete(int id) {
//		Session session = sessionFactory.getCurrentSession();
//		session.remove(session.get(Person.class, id));
//	}

}
