package com.example.demo.domain.todo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
//@Table(value="todo_list")
@Table(value="todo_list_data")
//@DynamicUpdate
@AllArgsConstructor
public class Todo implements Serializable {
	
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="index")
	@Column("index")
	//private Integer index; //todo_list
	private String index;
	
//	@Column(name="complete_yn")
	@Column("complete_yn")
	private Boolean completeYn;
	
//	@Column(name="contents")
	@Column("contents")
	private String contents;
	
//	@Column(name="creation_data")
	@Column("creation_date")
	private LocalDate creationDate;// = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

//	@Column(name="modified_data")
	@Column("modified_date")
	private LocalDate modifiedDate;// = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

	@With
	@Transient
	private List<TodoComment> commentList;

	@PersistenceConstructor
	public Todo(String index,Boolean completeYn,String contents,LocalDate creationDate,LocalDate modifiedDate) {
		this.index = index;
		this.completeYn = completeYn;
		this.contents = contents;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
	}

	public Todo(Todo todo, List<TodoComment> todoComment) {
		this.index = todo.getIndex();
		this.completeYn = todo.getCompleteYn();
		this.contents = todo.getContents();
		this.creationDate = todo.getCreationDate();
		this.modifiedDate = todo.getModifiedDate();
		this.commentList = todoComment;
	}
}
