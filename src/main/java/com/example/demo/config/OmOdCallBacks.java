package com.example.demo.config;

import com.example.demo.domain.om.OmOd;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
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
public class OmOdCallBacks implements BeforeConvertCallback<OmOd>, BeforeSaveCallback<OmOd> {

	@Override
	public Publisher<OmOd> onBeforeConvert(OmOd entity, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("1.BeforeConvertCallback_TodoCallBacks_onBeforeConvert");
		if (entity.getOdNo() == null || entity.getOdNo() == "") {
			entity.setMbNo("1");
			entity.setOdrNm("이시현");
			entity.setOdCmptDttm(LocalDateTime.now());
			entity.setRegDttm(LocalDateTime.now());
			entity.setOdNo("od"+new SimpleDateFormat ( "yyyyMMddHHmmss").format(new Date()));
			return Mono.just(entity);
		} else {
			entity.setModDttm(LocalDateTime.now());
			Mono<OmOd> omOd = Mono.just(entity);
			return omOd;
		}
	
	}

	@Override
	public Publisher<OmOd> onBeforeSave(OmOd entity, OutboundRow row, SqlIdentifier table) {
		// TODO Auto-generated method stub
		System.out.println("6.BeforeConvertCallback_TodoCallBacks_onBeforeSave");
		Mono<OmOd> omOd = Mono.just(entity);
		return omOd;
	}

}
