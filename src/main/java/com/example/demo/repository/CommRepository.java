package com.example.demo.repository;

import com.example.demo.domain.todo.TodoComment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CommRepository extends ReactiveCrudRepository<TodoComment, String> {//ReactiveCrudRepository<Todo, Integer> //todo_list_comment_data

	@Query("SELECT * FROM todo_list_comment_data WHERE todo_list_data_index = :index ORDER BY comment_index DESC")
	Flux<TodoComment> findTodoIndex(String index);

//	@Query("INSERT INTO todo_list (complete_yn,contents,creation_date,modified_date)VALUES('N',:doc,now(),NULL)")
//	Mono<Void> insert(String doc);
//	@Query("INSERT INTO todo_list_data (index,complete_yn,contents,creation_date,modified_date)VALUES(:index,'false',:doc,now(),NULL)")
//	Mono<Void> insert(String index, String doc);
//	@Query("SELECT nextval('index_seq')")
//	Mono<Integer> findSeq();

}