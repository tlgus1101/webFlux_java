package com.example.demo.repository;

import com.example.demo.domain.om.OmOd;
import com.example.demo.domain.om.OmOdDtl;
import com.example.demo.domain.om.OmOdEx;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OmOdRepository extends ReactiveCrudRepository<OmOd, String> {

    Flux<OmOd> findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(String odNo, String mbNo, String odrNm);

    @Modifying
    @Query("update om_od_dtl set mod_dttm = now() where od_no = :odNo")
    Mono<Integer>  update(String odNo);
}
