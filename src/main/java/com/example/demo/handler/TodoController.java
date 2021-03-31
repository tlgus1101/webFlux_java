package com.example.demo.handler;

import com.example.demo.domain.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TodoController {
	@Autowired
	private TodoRepository todoRepository;

	@GetMapping("/api/todoList_test")
	public Flux<Object> todoListTest(ServerRequest request) {
		System.out.println("todoRepository.findAllTodo()");
		return todoRepository.findAllTodo();
	}
}
