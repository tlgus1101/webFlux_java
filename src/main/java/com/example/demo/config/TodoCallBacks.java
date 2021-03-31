package com.example.demo.config;

import java.time.LocalDate;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

import com.example.demo.domain.todo.Todo;
import com.example.demo.repository.TodoRepository;

import reactor.core.publisher.Mono;

@Component
public class TodoCallBacks implements BeforeConvertCallback<Todo>, BeforeSaveCallback<Todo> {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public Publisher<Todo> onBeforeConvert(Todo entity, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("1.BeforeConvertCallback_TodoCallBacks_onBeforeConvert");
		if (entity.getIndex() == null || entity.getIndex() == "") {
			return todoRepository.findSeq().map(seq -> {
				entity.setCreationDate(LocalDate.now());
				entity.setCompleteYn(false);
				entity.setIndex("todo" + seq);
				return entity;
			});
		} else {
			entity.setModifiedDate(LocalDate.now());
			Mono<Todo> todo = Mono.just(entity);
			return todo;
		}
	
	}

	@Override
	public Publisher<Todo> onBeforeSave(Todo entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("6.BeforeConvertCallback_TodoCallBacks_onBeforeSave");
		Mono<Todo> todo = Mono.just(entity);
		return todo;
	}

}
