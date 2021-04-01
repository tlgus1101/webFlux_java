package com.example.demo.domain.om;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.domain.todo.Todo;
import com.example.demo.domain.todo.TodoComment;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_od")
public class OmOd implements Serializable, Persistable<String> {
	private static final long serialVersionUID = -5793348114310316331L;

	@Id
	private String odNo; //주문번호
	private String mbNo; //회원번호
	private String odrNm; //주문자명
	private String orglOdNo; //원주문번호
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime odCmptDttm; // 주문완료일시
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;

	@With
	@Transient
	private List<OmOdDtl> omOdDtlList;
	@With
	@Transient
	private List<OmOdFvrDtl> omOdFvrDtlList;

	private Boolean isNewBool;

	public OmOd(OmOdEx omOd, List<OmOdDtl> omOdDtlList) {
		this.odNo = omOd.getOdNo();
		this.mbNo = omOd.getMbNo();
		this.odrNm = omOd.getOdrNm();
		this.orglOdNo = omOd.getOrglOdNo();
		this.omOdDtlList = omOdDtlList;
	}

	public OmOd(OmOd omOd, List<OmOdDtl> omOdDtlList) {
		this.odNo = omOd.getOdNo();
		this.mbNo = omOd.getMbNo();
		this.odrNm = omOd.getOdrNm();
		this.orglOdNo = omOd.getOrglOdNo();
		this.odCmptDttm = omOd.getOdCmptDttm();
		this.regDttm = omOd.getRegDttm();
		this.modDttm = omOd.getModDttm();
		this.omOdDtlList = omOdDtlList;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isNew() {
		return this.isNewBool;
	}
}
