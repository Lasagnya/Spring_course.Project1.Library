package springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
	private int id;

	@Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
	private String name;

	@Min(value = 1800, message = "Birth should be greater than 1800")
	@Max(value = 3000, message = "Birth should be less than 3000")
	private int birth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int age) {
		this.birth = age;
	}
}
