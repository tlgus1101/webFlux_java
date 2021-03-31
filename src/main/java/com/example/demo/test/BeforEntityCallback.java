package com.example.demo.test;

import com.example.demo.domain.todo.Todo;
import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;

//@Component
public class BeforEntityCallback implements BeforeSaveCallback<Todo>,Ordered{

	@Override
	public Publisher<Todo> onBeforeSave(Todo entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("5.BeforeSaveCallback,Ordered_BeforEntityCallback_onBeforeSave");
		Mono<Todo> todo = Mono.just(entity);
		return todo;
	}

	@Override
	public int getOrder() {
		System.out.println("2.BeforeSaveCallback,Ordered_BeforEntityCallback_getOrder");
		// TODO Auto-generated method stub
		return 100;
	}

}
