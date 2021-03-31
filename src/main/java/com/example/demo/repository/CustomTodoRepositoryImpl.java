package com.example.demo.repository;

import com.example.demo.domain.todo.Todo;
import com.example.demo.domain.todo.TodoComment;
import com.example.demo.domain.todo.TodoEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
public class CustomTodoRepositoryImpl implements CustomTodoRepository {
	@Autowired
	private DatabaseClient client;
	@Autowired
	private R2dbcEntityTemplate r2dbcEntityTemplate;

	@Override
	public Flux<Object> findAllTodo() {
		//댓글 r2dbcEntityTemplate
		return r2dbcEntityTemplate.select(Todo.class)
				.from("todo_list_data")
				.all()
				.flatMap(data->{
					return Flux.just(data).zipWith(
							(r2dbcEntityTemplate.select(TodoComment.class)
									.from("todo_list_comment_data")
									.matching(query(where("todo_list_data_index").is(data.getIndex())))
									.all()
									.cache()
									.collectList()),
							(todo, todoComment) -> new TodoEx(todo, todoComment));
				});


//		String sql = "SELECT * FROM todo_list_data ORDER BY index desc";
//		String sqlComm = "SELECT * FROM todo_list_comment_data WHERE todo_list_data_index = :todo_list_data_index AND re_comm_index is null ORDER BY creation_date DESC";
//		String sqlReComm = "SELECT * FROM todo_list_comment_data WHERE re_comm_index = :re_comm_index ORDER BY creation_date DESC";
//
//		TodoMapper mapper = new TodoMapper();
//		TodoCommentMapper mapperComm = new TodoCommentMapper();

		//댓글
//		return client.sql(sql)
//				.map(mapper::apply)
//				.all()
//				.flatMap(data->{
//					return Flux.just(data).zipWith(
//							(client.sql(sqlComm)
//									.bind("todo_list_data_index",data.getIndex())
//									.map(mapperComm::apply)
//									.all()
//									.cache()
//									.collectList()),
//							(todo, todoComment) -> new TodoEx(todo, todoComment));
//				});
		
		//대댓글
//		return client.sql(sql)
//				.map(mapper::apply)
//				.all()
//				.flatMap(data->{
//					return Flux.just(data).zipWith(
//							(client.sql(sqlComm)
//									.bind("todo_list_data_index",data.getIndex())
//									.map(mapperComm::apply)
//									.all()
//									.flatMap(comm->{
//										return Flux.just(comm).zipWith((client.sql(sqlReComm)
//												.bind("re_comm_index",comm.getCommentIndex())
//												.map(mapperComm::apply)
//												.all()
//												.cache()
//												.collectList()),(co, rco) -> new TodoCommentEx(co, rco));
//									})
//									.cache()
//									.collectList()),
//							(todo, todoComment) -> new TodoEx(todo, todoComment));
//				});

//		return client.sql(sql)
//				.map(mapper::apply)
//				.all()
//				.flatMap(data->{
//							System.out.println(data);
//					return Flux.just(data).zipWith((client.sql(sqlComm)
//							.bind("todo_list_data_index",data.getIndex())
//							.map(mapperComm::apply)
//							.all().cache().collectList()),
//							(todo, todoComment) -> new TodoEx(todo, todoComment));
//				});

//		Flux<GroupedFlux<Object, TodoComment>> todoCommentFlux = client.sql(sql)
//					                                  .map(mapper::apply)
//					                                  .all()
//													  .flatMap(data-> {
//									  						return client.sql(sqlComm)
//											  							 .bind("todo_list_data_index",data.getIndex())
//											  							 .map(mapperComm::apply)
//											  							 .all().log("commIn");
//													  })
//				                                      .groupBy(comm->comm.getTodoListDataIndex());
	}

}
