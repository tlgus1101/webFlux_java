package com.example.demo.domain.om;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_od_dtl")
public class OmOdDtl implements Serializable {
	private static final long serialVersionUID = -5200824753588401614L;
	private String odNo; // 주문번호
	private Integer odSeq; //주문순번
	@Id
	private Integer procSeq; //처리순번
	private String clmNo; //클레임번호
	private String odTypCd; //주문유형코드 10 주문 20 취소 30 교환 40 반품
	private String odPrgsStepCd; //주문진행상태코드 01 주문완료 02 출고지시 03 상품준비 04 발송완료 04 배송완료 20 취소완료
	private String mbNo; //회원번호
	private Integer odQty; //주문수량
	private Integer cnclQty; //취소수량
	private Integer rtngQty; //반품수량
	private Integer xchgQty; //교환수량
	private Integer slPrc; //판매가
	private Integer dcAmt; //할인금액
	private String pdNo; //상품번호
	private String pdNm; //상품명
	private String prNo; //프로모션번
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime odCmptDttm; //주문완료일시
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime purCfrmDttm; // 구매확정일시
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;

	public OmOdDtl(OmOdDtl d) {
		this.odNo = d.odNo;
		this.odSeq = d.odSeq;
		this.procSeq = d.procSeq;
		this.clmNo = d.clmNo;
		this.odTypCd = d.odTypCd;
		this.odPrgsStepCd = d.odPrgsStepCd;
		this.mbNo = d.mbNo;
		this.odQty = d.odQty;
		this.cnclQty = d.cnclQty;
		this.rtngQty = d.rtngQty;
		this.xchgQty = d.xchgQty;
		this.slPrc = d.slPrc;
		this.dcAmt = d.dcAmt;
		this.pdNo = d.pdNo;
		this.pdNm = d.pdNm;
		this.prNo = d.prNo;
		this.odCmptDttm = d.odCmptDttm;
		this.purCfrmDttm = d.purCfrmDttm;
		this.regDttm = d.regDttm;
		this.modDttm = d.modDttm;
	}
}
