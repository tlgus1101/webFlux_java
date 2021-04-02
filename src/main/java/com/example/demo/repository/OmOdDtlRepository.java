package com.example.demo.repository;

import com.example.demo.domain.om.OmOdDtl;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface OmOdDtlRepository extends ReactiveCrudRepository<OmOdDtl, String> , CustomOmOdRepository{
    @Query("SELECT * FROM om_od_dtl WHERE od_no = :odno")
    Flux<OmOdDtl> findOdNoAll(String odNo);

    Flux<OmOdDtl> findByOdNoOrderByOdSeqAscProcSeq(String odNo);
    Flux<OmOdDtl> findByOdNo(String odNo);
    Flux<OmOdDtl> findByOdNoAndOdSeqOrderByProcSeqDesc(String odNo, Integer odSeq, Integer procSeq);
    Flux<OmOdDtl> findByOdNoAndOdSeqAndProcSeq(String odNo, Integer odSeq, Integer procSeq);

    @Modifying
    @Query("update om_od_dtl set cncl_qty = :cnclQty, mod_dttm = now() where od_no = :odNo And  od_seq = :odSeq And proc_seq = :procSeq")
    Mono<Integer> update(Integer cnclQty,String odNo,Integer odSeq,Integer procSeq);
    //Flux<OmOdDtl> findByOdNoAndOdTypCd(String odNo,String odTypCd);
    //Flux<OmOdDtl> findByOdNoAndOdTypCdGroupByOdNoAndOdSeq(String odNo,String odTypCd);
}
