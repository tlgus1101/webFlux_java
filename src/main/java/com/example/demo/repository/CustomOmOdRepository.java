package com.example.demo.repository;

import com.example.demo.domain.om.OmOdDtlFvrDtlResponse;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomOmOdRepository {
	Flux<OmOdDtlFvrDtlResponse> findClient();
}
