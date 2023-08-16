package springcourse.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {
	private final PeopleRepository peopleRepository;
	private final EntityManager entityManager;

	@Autowired
	public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager) {
		this.peopleRepository = peopleRepository;
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	public List<Person> findAll() {
		return peopleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Person> findOne(int id) {
		return peopleRepository.findById(id);
	}

	public void save(Person person) {
		peopleRepository.save(person);
	}

	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepository.save(updatedPerson);
	}

	public void delete(int id) {
		peopleRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<Book> showAssignedBooks(int id) {
		Session session = entityManager.unwrap(Session.class);
		Person person = session.get(Person.class, id);
		Hibernate.initialize(person.getBooks());
		return person.getBooks();
	}
}
