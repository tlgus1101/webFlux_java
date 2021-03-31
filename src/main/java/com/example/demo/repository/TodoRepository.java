package com.example.demo.repository;

import com.example.demo.domain.todo.ContentsOnly;
import com.example.demo.domain.todo.Todo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, String> ,CustomTodoRepository {//ReactiveCrudRepository<Todo, Integer> //todo_list
	@Query("SELECT * FROM todo_list_data ORDER BY index DESC")
	Flux<Todo> findAll();
//	@Query("INSERT INTO todo_list (complete_yn,contents,creation_date,modified_date)VALUES('N',:doc,now(),NULL)")
//	Mono<Void> insert(String doc);
	@Query("INSERT INTO todo_list_data (index,complete_yn,contents,creation_date,modified_date)VALUES(:index,'false',:doc,now(),NULL)")
	Mono<Void> insert(String index, String doc);
	@Query("SELECT nextval('index_seq')")
	Mono<Integer> findSeq();

	Flux<ContentsOnly> findByContents(String contents);
}
