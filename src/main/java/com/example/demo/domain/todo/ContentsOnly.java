package com.example.demo.domain.todo;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface ContentsOnly {
	//@Value("#{@todoBean.getFullCon(target)}")
	//@Value("#{args[0] + ' ' + target.contents + '!'}")
	String getContents();
	LocalDate getCreationDate();

	default String getFullCon(Todo todo) {
		return getContents().concat(" ").concat(getCreationDate().toString());
	}
}
