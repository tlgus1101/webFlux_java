package com.example.demo.repository;

import com.example.demo.domain.om.OmOd;
import com.example.demo.domain.om.OmOdDtl;
import com.example.demo.domain.om.OmOdEx;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OmOdRepository extends ReactiveCrudRepository<OmOd, String> {

    Flux<OmOd> findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(String odNo, String mbNo, String odrNm);
}
