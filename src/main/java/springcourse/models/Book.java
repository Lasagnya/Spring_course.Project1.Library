package springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
	private int id;

	@NotEmpty(message = "Title should not be empty.")
	@Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
	private String title;

	@NotEmpty(message = "Author should not be empty.")
	@Size(min = 3, max = 50, message = "Author should be between 3 and 50 characters")
	private String author;

	@Max(value = 3000, message = "Year should be less than 3000")
	private int year;
}
