package springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.BookDAO;
import springcourse.dao.PersonDAO;
import springcourse.models.Book;

@Controller
@RequestMapping("/books")
public class BooksController {
	private final BookDAO bookDAO;
	private final PersonDAO personDAO;

	@Autowired
	public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("books", bookDAO.index());
		return "books/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") int id) {
		if (bookDAO.show(id).isPresent()) {
			Book book = bookDAO.show(id).get();
			model.addAttribute("book", book);
			if (bookDAO.showAssignedPerson(id).isPresent())
				model.addAttribute("person", bookDAO.showAssignedPerson(id).get());
			else model.addAttribute("people", personDAO.index());
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
		bookDAO.save(book);
		return "redirect:/books";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		if (bookDAO.show(id).isPresent()) {
			model.addAttribute("book", bookDAO.show(id).get());
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
		bookDAO.update(id, book);
		return String.format("redirect:/books/%d", id);
	}

	@PatchMapping("/{id}/assign")
	public String assign(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
		bookDAO.assign(id, book);
		return String.format("redirect:/books/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		bookDAO.delete(id);
		return "redirect:/books";
	}

	@PatchMapping("/{id}/delete_assign")
	public String deleteAssign(@PathVariable("id") int id) {
		bookDAO.deleteAssign(id);
		return String.format("redirect:/books/%d", id);
	}
}
