package springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
	private int id;

	@NotEmpty(message = "Name should not be empty.")
	@Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
	private String name;

	@Min(value = 1, message = "Age should be greater than 1")
	@Max(value = 150, message = "Age should be less than 150")
	private int age;
}
