package com.example.demo.repository;

import com.example.demo.domain.om.OmOdDtlFvrDtlResponse;
import com.example.demo.domain.om.OmOdMapper;
import com.example.demo.domain.todo.TodoCommentMapper;
import com.example.demo.domain.todo.TodoEx;
import com.example.demo.domain.todo.TodoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomOmOdRepositoryImpl implements CustomOmOdRepository {
	@Autowired
	private DatabaseClient client;
	@Autowired
	private R2dbcEntityTemplate r2dbcEntityTemplate;
	ObjectMapper objectMapper;

	@Override
	public Flux<OmOdDtlFvrDtlResponse> findClient() {
		String sql =  "select *\n" +
				" from om_od_dtl as a\n" +
				"    , om_od_fvr_dtl as b\n" +
				"where a.od_no = :odNo\n" +
				"  and a.od_no = b.od_no\n" +
				"  and a.od_seq = b.od_seq\n" +
				"  and a.proc_seq = b.proc_seq\n" +
				"  and coalesce(b.aply_qty, 0) - coalesce(b.cncl_qty, 0) > 0";

		OmOdMapper mapper = new OmOdMapper();

		//댓글
		return client.sql(sql)
				.bind("odNo", "od20210402143637")
				.map(mapper::apply)
				.all();
	}

}
