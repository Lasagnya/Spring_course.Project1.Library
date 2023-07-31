package springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.PersonDAO;
import springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private final PersonDAO personDAO;

	@Autowired
	public PeopleController(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("people", personDAO.index());
		return "people/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") int id) {
		if (personDAO.show(id).isPresent())
			model.addAttribute("person", personDAO.show(id).get());
		else {
			model.addAttribute("id", id);
			return "people/incorrect_id";
		}
		return "people/show";
	}

	@GetMapping("/new")
	public String newPerson(Model model) {
		model.addAttribute("person", new Person());
		return "people/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "people/new";
		personDAO.save(person);
		return "redirect:/people";
	}
}
