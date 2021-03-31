package com.example.demo.domain.todo;

import org.springframework.stereotype.Component;

//@Component
public class TodoBean{

	String getFullCon(Todo todo) {
		return todo.getContents();
	}

}
