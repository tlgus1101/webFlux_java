package com.example.demo.config;

import com.example.demo.domain.om.OmOdDtl;
import com.example.demo.domain.om.OmOdFvrDtl;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Component
public class OmOdFvrDtlCallBacks implements BeforeConvertCallback<OmOdFvrDtl>, BeforeSaveCallback<OmOdFvrDtl>, AfterConvertCallback<OmOdFvrDtl> {

	@Override
	public Publisher<OmOdFvrDtl> onBeforeConvert(OmOdFvrDtl entity, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("1.BeforeConvertCallback_TodoCallBacks_onBeforeConvert");
		if (entity.getOdFvrNo() == null || entity.getOdFvrNo() == "") {
			entity.setOdFvrNo( new Random().nextInt(100) + new SimpleDateFormat( "yyyyMMddHHmmss").format(new Date()));
			entity.setRegDttm(LocalDateTime.now());
			return Mono.just(entity);
		} else {
			entity.setModDttm(LocalDateTime.now());
			Mono<OmOdFvrDtl> omOdFvrDtl = Mono.just(entity);
			return omOdFvrDtl;
		}
	
	}

	@Override
	public Publisher<OmOdFvrDtl> onBeforeSave(OmOdFvrDtl entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("6.BeforeConvertCallback_TodoCallBacks_onBeforeSave");
		Mono<OmOdFvrDtl> omOdFvrDtl = Mono.just(entity);
		return omOdFvrDtl;
	}

	@Override
	public Publisher<OmOdFvrDtl> onAfterConvert(OmOdFvrDtl entity, SqlIdentifier table) {
		System.out.println("저장되고 나서????");
		System.out.println(entity);
		return null;
	}
}
