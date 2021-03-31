package com.example.demo.handler;

import com.example.demo.repository.CommRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.domain.todo.Todo;
import com.example.demo.repository.TodoRepository;

//import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

//@Slf4j
//@EnableR2dbcRepositories
//@Service
//@Configuration
@Component
@Controller
public class TodoHandler {
//	private HashMap<Object, Object> result = new HashMap<>();
//	private Mono<HashMap<Object, Object>> mapper = Mono.just(result);

	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private CommRepository commRepository;

	public Mono<ServerResponse> insert(ServerRequest request) {
		return request.bodyToMono(Todo.class).flatMap(todo -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(todoRepository.save(todo), Todo.class)));
//		return todoRepository.findSeq().flatMap(todo -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.insert("todo"+todo, request.queryParam("contents").get()), Todo.class)));
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		return ServerResponse.ok().build(todoRepository.deleteById(request.queryParam("index").get()));
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		return request.bodyToMono(Todo.class).flatMap(todo -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(todoRepository.save(todo), Todo.class)));
		
//		return request.bodyToMono(Todo.class).map(todo -> {
//			todo.setModifiedDate(LocalDate.now());
//			return todo;
//		}).flatMap(todo -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.save(todo), Todo.class)));
	}

	public Mono<ServerResponse> todoList(ServerRequest request) {
		////-----댓글 달기 전
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.findAll(), Todo.class));
		/////댓글 달기 전-----
		////-----댓글 불러오기 customTodoRepo
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.findAllTodo(), Todo.class)); //댓글 불러오기 customTodoRepo
		/////댓글 불러오기 customTodoRepo-----
		////-----댓글 불러오기
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.findAll()
//						.flatMap(data-> Flux.just(data).zipWith((commRepository.findTodoIndex(data.getIndex()).cache()
//									.collectList()),(todo, todoComment) -> new Todo(todo, todoComment))
//						), Todo.class));
		/////댓글 불러오기-----
		/////---withCommentList 조회
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(todoRepository.findAll()
						.flatMap(comm->Mono.just(comm)
									.zipWith(commRepository.findTodoIndex(comm.getIndex()).cache().collectList()))
						.map(todo->todo.getT1().withCommentList(todo.getT2())), Todo.class)); //댓글 불러오기
		/////withCommentList 조회-----
		/////--- 분리 조회
//		Flux<Todo> todoFlux = todoRepository.findAll().cache();
//		Flux<List<TodoComment>> commFlux = todoFlux.flatMap(data->commRepository.findTodoIndex(data.getIndex()).cache().collectList());
//		return todoFlux.zipWith(commFlux.collectList())
//		return todoFlux.zipWith(commFlux, (todo, todoComment) -> new TodoEx(todo, todoComment)).collectList().flatMap(ServerResponse.ok()::bodyValue);
		///// 분리 조회 ---
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(todoRepository.findByContents("test").map(data->{System.out.println(data); return data;}), Todo.class));

	}

}
