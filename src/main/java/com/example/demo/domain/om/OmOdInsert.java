package com.example.demo.domain.om;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OmOdInsert implements Serializable {
	private static final long serialVersionUID = 2028689674664550029L;
	List<OmOdDtl> omOdDtlList;
	List<OmOdFvrDtl> omOdFvrDtlList;
}
