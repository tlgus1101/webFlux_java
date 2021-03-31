package com.example.demo.test;


import com.example.demo.domain.todo.Todo;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;

//@Order(1)
//@Component
public class BaforeSaveFirst implements BeforeSaveCallback<Todo> {

	@Override
	public Publisher<Todo> onBeforeSave(Todo entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		Mono<Todo> todo = Mono.just(entity);
		System.out.println("4.BeforeSaveCallback_BaforeSaveFirst_onBeforeSave");

		return todo;
	}
}
