package springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springcourse.dao.PersonDAO;
import springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
	private final PersonDAO personDAO;

	@Autowired
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public boolean supports(Class<?> clazz) {	//для какой сущности валидатор предназначен
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		if (personDAO.show(person.getName()).isPresent())
			if (personDAO.show(person.getName()).get().getId() != person.getId())
				errors.rejectValue("name", "", "This name is already taken.");
	}
}
