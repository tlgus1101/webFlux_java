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
public class TodoMapper implements BiFunction<Row,Object,Todo> {
	@Override
	public Todo apply(Row row, Object o) {
		String index = row.get("index",String.class);
		Boolean completeYn = row.get("complete_yn",Boolean.class);
		String contents = row.get("contents",String.class);
		LocalDate creationDate = row.get("creation_date",LocalDate.class);
		LocalDate modifiedDate = row.get("modified_date",LocalDate.class);
		Todo todo = new Todo(index,completeYn,contents,creationDate,modifiedDate);
		return todo;
	}
}
