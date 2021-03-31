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

@Data
@Getter
@Setter
//@Table(value="todo_list")
@Table(value="todo_list_comment_data")
//@DynamicUpdate
@AllArgsConstructor
public class TodoComment implements Serializable {
	
	@Id
	@Column("comment_index")
	private String commentIndex;

	@Column("todo_list_data_index")
	private String todoListDataIndex;

	@Column("contents")
	private String contents;

	@Column("re_comm_index")
	private String reCommIndex;

	@Column("creation_date")
	private LocalDate creationDate;

	@Column("modified_date")
	private LocalDate modifiedDate;
	

}
