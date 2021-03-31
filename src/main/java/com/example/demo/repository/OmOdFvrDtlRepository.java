package com.example.demo.repository;

import com.example.demo.domain.om.OmOdDtl;
import com.example.demo.domain.om.OmOdFvrDtl;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OmOdFvrDtlRepository extends ReactiveCrudRepository<OmOdFvrDtl, String> {
    Flux<OmOdFvrDtl> findByOdNoAndOdSeqAndProcSeq(String odNo,Integer odSeq,Integer procSeq);

    @Modifying
    @Query("update om_od_fvr_dtl set cncl_qty = :cnclQty, mod_dttm = now() where od_no = :odNo And  od_seq = :odSeq And proc_seq = :procSeq")
    Mono<Integer> update(Integer cnclQty, String odNo, Integer odSeq, Integer procSeq);
}
