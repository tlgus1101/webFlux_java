package com.example.demo.domain.om;

import com.example.demo.domain.todo.Todo;
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
public class OmOdMapper implements BiFunction<Row,Object, OmOdDtlFvrDtlResponse> {
	@Override
	public OmOdDtlFvrDtlResponse apply(Row row, Object o) {

		String odNo = row.get("od_no",String.class);
		String mbNo = row.get("mb_no",String.class);
		Integer odSeq = row.get("od_seq",Integer.class);
		Integer procSeq = row.get("proc_seq",Integer.class);
		Integer odQty = row.get("od_qty",Integer.class);
		Integer cnclQty = row.get("cncl_qty",Integer.class);
		Integer slPrc = row.get("sl_prc",Integer.class);
		Integer dcAmt = row.get("dc_amt",Integer.class);
		String dcTnnoCd = row.get("dc_tnno_cd",String.class);
		Integer fvrAmt = row.get("fvr_amt",Integer.class);
		String prNo = row.get("pr_no",String.class);
		String prNm = row.get("pr_nm",String.class);
		String cpnNo = row.get("cpn_no",String.class);
		String cpnNm = row.get("cpn_nm",String.class);

		OmOdDtlFvrDtlResponse omOdDtlFvrDtlResponse = new OmOdDtlFvrDtlResponse(odNo,mbNo,odSeq,procSeq,odQty,cnclQty,slPrc,dcAmt,dcTnnoCd,fvrAmt,prNo,prNm,cpnNo,cpnNm);

		return omOdDtlFvrDtlResponse;
	}
}
