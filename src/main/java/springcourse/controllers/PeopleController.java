package springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.PersonDAO;
import springcourse.models.Person;
import springcourse.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private final PersonDAO personDAO;
	private final PersonValidator personValidator;

	@Autowired
	public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
		this.personDAO = personDAO;
		this.personValidator = personValidator;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("people", personDAO.index());
		return "people/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") int id) {
		if (personDAO.show(id).isPresent()) {
			model.addAttribute("person", personDAO.show(id).get());
			return "people/show";
		}
		else {
			model.addAttribute("id", id);
			return "people/incorrect_id";
		}
	}

	@GetMapping("/new")
	public String newPerson(Model model) {
		model.addAttribute("person", new Person());
		return "people/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/new";
		personDAO.save(person);
		return "redirect:/people";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		if (personDAO.show(id).isPresent()) {
			model.addAttribute("person", personDAO.show(id).get());
			return "people/edit";
		}
		else {
			model.addAttribute("id", id);
			return "people/incorrect_id";
		}
	}

	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/edit";
		personDAO.update(id, person);
		return "redirect:/people";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDAO.delete(id);
		return "redirect:/people";
	}
}
