package com.example.demo.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TodoEx implements Serializable {

	@Id
	private String index;
	private Boolean completeYn;
	private String contents;
	private LocalDate creationDate;// = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
	private LocalDate modifiedDate;// = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
	private List<TodoComment> commentList;
	//private List<TodoCommentEx> commentList;

//	public TodoEx(Todo todo, List<TodoCommentEx> todoComment) {
//		this.index = todo.getIndex();
//		this.completeYn = todo.getCompleteYn();
//		this.contents = todo.getContents();
//		this.creationDate = todo.getCreationDate();
//		this.modifiedDate = todo.getModifiedDate();
//		this.commentList = todoComment;
//	}

	public TodoEx(Todo todo, List<TodoComment> todoComment) {
		this.index = todo.getIndex();
		this.completeYn = todo.getCompleteYn();
		this.contents = todo.getContents();
		this.creationDate = todo.getCreationDate();
		this.modifiedDate = todo.getModifiedDate();
		this.commentList = todoComment;
	}
}
