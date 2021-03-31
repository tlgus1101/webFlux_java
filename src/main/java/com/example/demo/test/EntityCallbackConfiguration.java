package com.example.demo.test;

import com.example.demo.domain.todo.Todo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;

//@Configuration
public class EntityCallbackConfiguration {

	@Bean
	BeforeSaveCallback<Todo> EntityCallback(){
		BeforeSaveCallback<Todo> t = null;
		System.out.println("3333333333333333333333333");
		return (BeforeSaveCallback<Todo>) t;
	}
}
