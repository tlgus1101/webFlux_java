package com.example.demo.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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
public class TodoCommentEx implements Serializable {
	
	@Id
	@Column("comment_index")
	private String commentIndex;

	@Column("todo_list_data_index")
	private String todoListDataIndex;

	@Column("contents")
	private String contents;

	private String reCommIndex;

	@Column("creation_date")
	private LocalDate creationDate;

	@Column("modified_date")
	private LocalDate modifiedDate;

	private List<TodoComment> reCommentList;

	public TodoCommentEx(TodoComment comm, List<TodoComment> reCommentList) {
		this.commentIndex = comm.getCommentIndex();
		this.contents = comm.getContents();
		this.creationDate = comm.getCreationDate();
		this.modifiedDate = comm.getModifiedDate();
		this.reCommentList = reCommentList;
	}
}
