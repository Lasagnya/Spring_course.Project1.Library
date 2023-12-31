package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.util.BookMapper;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Book> index() {
		return jdbcTemplate.query("select * from book", new BookMapper());
	}

	public Optional<Book> show(int id) {
		return jdbcTemplate.query("select * from book where id=?",
						new Object[] {id}, new int[] {Types.INTEGER}, new BookMapper())
				.stream().findAny();
	}

	public void save(Book book) {
		int[] argTypes = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
		jdbcTemplate.update("insert into book(title, author, year) values(?, ?, ?)",
				new Object[]{book.getTitle(), book.getAuthor(), book.getYear()}, argTypes);
	}

	public void update(int id, Book updatedBook) {
		//System.out.println(updatedBook.getTitle());
		int[] argTypes = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
		jdbcTemplate.update("update book set title=?, author=?, year=? where id=?",
				new Object[]{updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id}, argTypes);
	}

	public void assign(int id, Book assignedBook) {
		jdbcTemplate.update("update book set person_id=? where id=?",
				new Object[]{assignedBook.getPerson_id(), id}, new int[]{Types.INTEGER, Types.INTEGER});
	}

	public void delete(int id) {
		jdbcTemplate.update("delete from book where id=?", new Object[]{id}, new int[] {Types.INTEGER});
	}

	public void deleteAssign(int id) {
		jdbcTemplate.update("update book set person_id=null where id=?",
				new Object[]{id}, new int[] {Types.INTEGER});
	}

	public Optional<Person> showAssignedPerson(int id) {
		return jdbcTemplate.query("select person.id, name, birth from person join book on person.id = book.person_id where book.id=?",
				new Object[]{id}, new int[] {Types.INTEGER}, new BeanPropertyRowMapper<>(Person.class))
				.stream().findAny();
	}
}
