package com.example.demo.config;

import com.example.demo.domain.om.OmOd;
import com.example.demo.domain.om.OmOdDtl;
import com.example.demo.domain.om.OmOdFvrDtl;
import com.example.demo.repository.OmOdDtlRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class OmOdDtlCallBacks implements BeforeConvertCallback<OmOdDtl>, BeforeSaveCallback<OmOdDtl>, AfterSaveCallback<OmOdDtl> {

	@Autowired
	OmOdDtlRepository omOdDtlRepository;

	@Override
	public Publisher<OmOdDtl> onBeforeConvert(OmOdDtl entity, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("1.BeforeConvertCallback_TodoCallBacks_onBeforeConvert");
		if (entity.getProcSeq() == null || entity.getProcSeq() == 0) {
			if(entity.getOdTypCd() == null){
					entity.setProcSeq(1);
					entity.setMbNo("1");
					entity.setOdTypCd("10");
					entity.setOdPrgsStepCd("01");
					entity.setOdCmptDttm(LocalDateTime.now());
					entity.setRegDttm(LocalDateTime.now());
					return Mono.just(entity);
			}else{
				return  omOdDtlRepository.findByOdNoAndOdSeqOrderByProcSeqDesc(entity.getOdNo(),entity.getOdSeq(),entity.getProcSeq())
						.map(data->{
							entity.setProcSeq(data.getProcSeq()+1);
//							entity.setClmNo("test1");
							entity.setModDttm(LocalDateTime.now());
							entity.setRegDttm(LocalDateTime.now());
							return entity;
						});
			}
		} else {
			return omOdDtlRepository.findByOdNoAndOdSeqAndProcSeq(entity.getOdNo(),entity.getOdSeq(),entity.getProcSeq())
					.map(data->{
						entity.setCnclQty(entity.getOdQty());
						entity.setModDttm(LocalDateTime.now());
						return entity;
					});
		}
	
	}

	@Override
	public Publisher<OmOdDtl> onBeforeSave(OmOdDtl entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("6.BeforeConvertCallback_TodoCallBacks_onBeforeSave");
		Mono<OmOdDtl> omOdDtl = Mono.just(entity);
		return omOdDtl;
	}

	@Override
	public Publisher<OmOdDtl> onAfterSave(OmOdDtl entity, OutboundRow outboundRow, SqlIdentifier table) {
		System.out.println(entity);
		return Mono.just(entity);
	}
}
