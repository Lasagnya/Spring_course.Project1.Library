package springcourse.services;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
	private final BooksRepository booksRepository;
	private final EntityManager entityManager;

	@Autowired
	public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
		this.booksRepository = booksRepository;
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	public List<Book> findAll(Boolean sort) {
		if (sort != null && sort)
			return booksRepository.findAll(Sort.by("year"));
		else return booksRepository.findAll();
	}

	public List<Book> findAll(Integer page, Integer size, Boolean sort) {
		if (sort != null && sort)
			return booksRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
		else return booksRepository.findAll(PageRequest.of(page, size)).getContent();
	}

	@Transactional(readOnly = true)
	public Optional<Book> findOne(int id) {
		return booksRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Book> findByTitleStartingWith(String start) {
		return booksRepository.findByTitleStartingWith(start);
	}

	public void save(Book book) {
		booksRepository.save(book);
	}

	public void update(int id, Book updatedBook) {
		Book bookToBeUpdated = findOne(id).get();
		updatedBook.setId(id);
		updatedBook.setOwner(bookToBeUpdated.getOwner());
		booksRepository.save(updatedBook);
	}

	public void delete(int id) {
		booksRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Person showAssignedPerson(int id) {
		Session session = entityManager.unwrap(Session.class);
		Book book = session.get(Book.class, id);
		Hibernate.initialize(book.getOwner());
		return book.getOwner();
	}

	public void assign(int id, Person oldPerson) {
		Session session = entityManager.unwrap(Session.class);
		Book book = session.get(Book.class, id);
		Person person = session.get(Person.class, oldPerson.getId());
		book.setOwner(person);
		book.setTakingTime(new Date());
		person.getBooks().add(book);
	}

	public void deleteAssign(int id) {
		Session session = entityManager.unwrap(Session.class);
		Book book = session.get(Book.class, id);
		book.getOwner().getBooks().remove(book);
		book.setOwner(null);
		book.setTakingTime(null);
	}
}

