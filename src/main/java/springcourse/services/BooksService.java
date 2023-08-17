package springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public BooksService(BooksRepository booksRepository) {
		this.booksRepository = booksRepository;
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
		return booksRepository.findById(id).map(Book::getOwner).orElse(null);
	}

	public void assign(int id, Person assignedPerson) {
		booksRepository.findById(id).ifPresent(book -> {
			book.setOwner(assignedPerson);
			book.setTakingTime(new Date());
		});
	}

	public void deleteAssign(int id) {
		booksRepository.findById(id).ifPresent(book -> {
			book.setOwner(null);
			book.setTakingTime(null);
		});
	}
}

