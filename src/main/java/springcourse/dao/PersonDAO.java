package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Person> index() {
		return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
	}

	public Optional<Person> show(int id) {
		return jdbcTemplate.query("select * from person where id=?", new Object[] {id}, new int[] {Types.INTEGER}, new BeanPropertyRowMapper<>(Person.class))
				.stream().findAny();
	}

	public void save(Person person) {
		int[] argTypes = new int[] {Types.VARCHAR, Types.INTEGER};
		jdbcTemplate.update("insert into person(name, birth) values(?, ?)", new Object[]{person.getName(), person.getBirth()}, argTypes);
	}

	public void update(int id, Person updatedPerson) {
		int[] argTypes = new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER};
		jdbcTemplate.update("update person set name=?, birth=? where id=?",
				new Object[]{updatedPerson.getName(), updatedPerson.getBirth(), id}, argTypes);
	}

	public void delete(int id) {
		jdbcTemplate.update("delete from person where id=?", new Object[]{id}, new int[] {Types.INTEGER});
	}
}
