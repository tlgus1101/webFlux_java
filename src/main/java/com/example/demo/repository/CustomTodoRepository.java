package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomTodoRepository{
	Flux<Object> findAllTodo();
}
