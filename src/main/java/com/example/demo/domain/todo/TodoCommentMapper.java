package com.example.demo.domain.todo;

import io.r2dbc.spi.Row;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Data
@Getter
@Setter
//@Table(value="todo_list")
@Table(value="todo_list_data")
//@DynamicUpdate
public class TodoCommentMapper implements BiFunction<Row,Object,TodoComment> {
	@Override
	public TodoComment apply(Row row, Object o) {
		String commentIndex = row.get("comment_index",String.class);
		String todoListDataIndex = row.get("todo_list_data_index",String.class);
		String reCommIndex = row.get("re_comm_index",String.class);
		String contents = row.get("contents",String.class);
		LocalDate creationDate = row.get("creation_date",LocalDate.class);
		LocalDate modifiedDate = row.get("modified_date",LocalDate.class);

		TodoComment todoComm = new TodoComment(commentIndex,todoListDataIndex,contents,reCommIndex,creationDate,modifiedDate);
		return todoComm;
	}
}
