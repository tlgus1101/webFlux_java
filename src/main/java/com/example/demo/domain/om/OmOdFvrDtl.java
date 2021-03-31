package com.example.demo.domain.om;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_od_fvr_dtl")
public class OmOdFvrDtl implements Serializable {
	private static final long serialVersionUID = 2028689674664550029L;

	@Id
	private String odFvrNo; //주문혜택번호
	private String odNo; // 주문번호
	private Integer odSeq; //주문순번
	private Integer procSeq; //처리순번
	private String clmNo; //클레임번호
	private String orglOdFvrNo; //원주문혜택번호
	private String odFvrDvsCd; // 주문혜택구분코드 발생 HAPN 취소 CNCL
	private String dcTnnoCd; //할인차수코드 1차 1st 2차 2nd 3차 3rd 4차 4th 5차 5th
	private Integer aplyQty; //적용수량
	private Integer cnclQty; //취소수량
	private Integer fvrAmt; //혜택금액
	private String prNo; //프로모션번호
	private String prNm; //프로모션명
	private String cpnNo; //쿠폰번호
	private String cpnNm; //쿠폰명
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;
}
