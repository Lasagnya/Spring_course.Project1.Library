package springcourse.util;

import org.springframework.jdbc.core.RowMapper;
import springcourse.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("id"));
		//book.setPerson_id(rs.getInt("person_id"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setYear(rs.getInt("year"));
		return book;
	}
}
