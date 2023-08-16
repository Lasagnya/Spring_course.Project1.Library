package springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.PersonDAO;
import springcourse.models.Book;
import springcourse.models.Person;
import springcourse.services.BooksService;
import springcourse.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
	//private final BookDAO booksService;
	//private final PersonDAO personDAO;
	private final BooksService booksService;
	private final PeopleService peopleService;

	@Autowired
	public BooksController(BooksService booksService, PeopleService peopleService) {
		this.booksService = booksService;
		this.peopleService = peopleService;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("books", booksService.findAll());
		return "books/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
		if (booksService.findOne(id).isPresent()) {
			model.addAttribute("book", booksService.findOne(id).get());
			if (booksService.showAssignedPerson(id) != null)
				model.addAttribute("person", booksService.showAssignedPerson(id));
			else model.addAttribute("people", peopleService.findAll());
			return "books/show";
		}
		else {
			model.addAttribute("id", id);
			return "books/incorrect_id";
		}
	}

	@GetMapping("/new")
	public String newBook(Model model) {
		model.addAttribute("book", new Book());
		return "books/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "books/new";
		booksService.save(book);
		return "redirect:/books";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		if (booksService.findOne(id).isPresent()) {
			model.addAttribute("book", booksService.findOne(id).get());
			return "books/edit";
		}
		else {
			model.addAttribute("id", id);
			return "books/incorrect_id";
		}
	}

	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "books/edit";
		booksService.update(id, book);
		return String.format("redirect:/books/%d", id);
	}

	@PatchMapping("/{id}/assign")
	public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
		booksService.assign(id, person);
		return String.format("redirect:/books/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		booksService.delete(id);
		return "redirect:/books";
	}

	@PatchMapping("/{id}/delete_assign")
	public String deleteAssign(@PathVariable("id") int id) {
		booksService.deleteAssign(id);
		return String.format("redirect:/books/%d", id);
	}
}
