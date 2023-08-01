package springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public class Book {
	private int id;

	private int person_id;

	@Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
	private String title;

	@Size(min = 3, max = 50, message = "Author should be between 3 and 50 characters")
	private String author;

	@Max(value = 3000, message = "Year should be less than 3000")
	private int year;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
}
