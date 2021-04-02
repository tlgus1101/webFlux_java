package com.example.demo.handler;

import com.example.demo.domain.om.*;
import com.example.demo.repository.OmOdDtlRepository;
import com.example.demo.repository.OmOdFvrDtlRepository;
import com.example.demo.repository.OmOdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Component
@Controller

public class OmOdHandler {
	@Autowired
	private OmOdRepository omOdRepository;
	@Autowired
	private OmOdDtlRepository omOdDtlRepository;
	@Autowired
	private OmOdFvrDtlRepository omOdFvrDtlRepository;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Mono<ServerResponse> omOdList(ServerRequest request){
		return returnServerResponse(request,this::omOdDtlListFunction);
	}
	public Mono<ServerResponse> omOdCancelList(ServerRequest request){
		return returnServerResponse(request,this::omOdDtlCancelListFunction);
	}
	public Mono<ServerResponse> odPrgsStepCdList(ServerRequest request) {
		return returnServerResponse(request,this::odPrgsStepCdListFunction);
	}
	public Mono<ServerResponse> returnServerResponse(ServerRequest request,Function<OmOd,Mono<List<OmOdDtl>>> omOdDtlList){
		return request.bodyToMono(OmOd.class).flatMap(param -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(
						omOdFlux(param,omOdDtlList)
						,OmOd.class)));
	}
	public Flux<OmOd> omOdFlux(OmOd param, Function<OmOd,Mono<List<OmOdDtl>>> omOdDtlList){
		return Flux.just(param)
				.flatMap(this::omOdListFunction)
				.concatMap(data->
						Flux.just(data).zipWith(Flux.just(data).flatMap(omOdDtlList)
								,(omOd,omOdDtl)->new OmOd(omOd,omOdDtl)));
	}
	public Flux<OmOd> omOdListFunction(OmOd param) {
		return omOdRepository.findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(param.getOdNo(),param.getMbNo(),param.getOdrNm())
				.concatMap(data-> Flux.just(data)
						.zipWith(omOdDtlListFunction(data),
								(omOd,omOdDtl)->new OmOd(omOd, omOdDtl)));
	}
	public Mono<List<OmOdDtl>> omOdDtlListFunction(OmOd data) {
		return omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
				.groupBy(OmOdDtl::getOdSeq)
				.concatMap(groupedFlux-> groupedFlux.last().map(d-> d))
				.collectList();
	}
	public Mono<List<OmOdDtl>> omOdDtlCancelListFunction(OmOd data) {
		return omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
				.groupBy(OmOdDtl::getOdSeq)
				.concatMap(groupedFlux-> groupedFlux.last())
				.filter(omOdDtl->omOdDtl.getOdTypCd().equals("20"))
				.collectList();
	}
	public Mono<List<OmOdDtl>> odPrgsStepCdListFunction(OmOd data) {
		return omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
				.groupBy(OmOdDtl::getOdSeq)
				.concatMap(groupedFlux-> groupedFlux.last())
				.filter(omOdDtl->Integer.parseInt(omOdDtl.getOdPrgsStepCd()) >= 2)
				.collectList();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
	public Mono<ServerResponse> omOdInsert(ServerRequest request) {
		return request.bodyToMono(OmOdInsert.class)
				.flatMap(omOdDtl -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromProducer(
								omOdRepository.save(new OmOd()).flatMap(data -> Flux.fromIterable(omOdDtl.getOmOdDtlList()).flatMap(dtl->{
									dtl.setOdNo(data.getOdNo());
									return omOdDtlRepository.save(dtl)
											.flatMap(dtlData->
													Flux.fromIterable(omOdDtl.getOmOdFvrDtlList())
															.flatMap(fvr->{
																if(fvr.getOdSeq() == dtlData.getOdSeq()){
																	fvr.setOdNo(dtlData.getOdNo());
																	fvr.setOdSeq(dtlData.getOdSeq());
																	fvr.setProcSeq(dtlData.getProcSeq());
																	return omOdFvrDtlRepository.save(fvr);
																}else{
																	return Mono.just(dtlData);
																}
															}).collectList()
											);
								}).collectList())
								, OmOdInsert.class)));
	}

	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
	public Mono<ServerResponse> cancelOmOd(ServerRequest request) {
		return Flux.combineLatest(Flux.just("clmNoTTTT"),request.bodyToFlux(OmOdDtl.class),(t1, t2) -> {
				t2.setClmNo(t1);
				return t2;})
				.flatMap(omOdDtl->Mono.just(omOdDtl).zipWith(omOdRepository.update(omOdDtl.getOdNo()),(T1,T2)->T1))
				.flatMap(omOdDtl->omOdDtlRepository.save(omOdDtl))
				.flatMap(dtlSave->Mono.just(dtlSave).zipWith(omOdDtlRepository.update(dtlSave.getOdQty(),dtlSave.getOdNo(),dtlSave.getOdSeq(),dtlSave.getProcSeq()-1),(T1,T2)->T1))
				.flatMap(omOdDtl->{
					omOdDtl.setProcSeq(omOdDtl.getProcSeq()-1);
					return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq())
							.concatMap(fvr->{
								fvr.setProcSeq(fvr.getProcSeq() + 1);
								fvr.setClmNo(omOdDtl.getClmNo());
								fvr.setOrglOdFvrNo(fvr.getOdFvrNo());
								fvr.setOdFvrDvsCd("CNCL");
								fvr.setModDttm(LocalDateTime.now());
								fvr.setOdFvrNo(null);
								return omOdFvrDtlRepository.save(fvr)
										.flatMap(fvrSave->omOdFvrDtlRepository.update(fvrSave.getAplyQty(),fvrSave.getOdNo(),fvrSave.getOdSeq(),fvrSave.getProcSeq()-1));
							});
					}).collectList()
				.flatMap(omOdDtl -> ServerResponse.ok().body(BodyInserters.fromProducer(Mono.just(omOdDtl),OmOdDtl.class)));
	}

//	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
//	public Mono<ServerResponse> omOdInsert(ServerRequest request) {
//		return request.bodyToMono(OmOdInsert.class)
//				.flatMap(omOdDtl -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//						.body(BodyInserters.fromProducer(
//								omOdRepository.save(new OmOd()).flatMap(data -> Flux.fromIterable(omOdDtl.getOmOdDtlList()).flatMap(dtl->{
//									dtl.setOdNo(data.getOdNo());
//									return omOdDtlRepository.save(dtl)
//											.flatMap(dtlData->
//													Flux.fromIterable(omOdDtl.getOmOdFvrDtlList())
//															.flatMap(fvr->{
//																if(fvr.getOdSeq() == dtlData.getOdSeq()){
//																	fvr.setOdNo(dtlData.getOdNo());
//																	fvr.setOdSeq(dtlData.getOdSeq());
//																	fvr.setProcSeq(dtlData.getProcSeq());
//																	return omOdFvrDtlRepository.save(fvr);
//																}else{
//																	return Mono.just(dtlData);
//																}
//															}).collectList()
//											);
//								}).collectList())
//								, OmOdInsert.class)));
//
////				return request.bodyToMono(OmOdInsert.class)
////				.flatMap(omOdDtl -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
////				.body(BodyInserters.fromProducer(
////								Flux.fromIterable(
////										omOdDtl.getOmOdDtlList())
////								.concatMap(dtl->{
////							return Mono.just(dtl);
////						})
////				, OmOdInsert.class)));
//
//	}
//	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
//	public Mono<ServerResponse> cancelOmOdList(ServerRequest request) {
//		return request.bodyToMono(OmOd.class)
//				.flatMap(omOd -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//						.body(BodyInserters.fromProducer(
//								Flux.fromIterable(omOd.getOmOdDtlList()).flatMap(omOdDtl ->{
//									return omOdDtlRepository.save(omOdDtl)
//											.flatMap(data->{
//												omOdDtl.setProcSeq(data.getProcSeq()-1);
//												omOdDtl.setCnclQty(data.getOdQty());
//												return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq())
//														.concatMap(fvr->{
//															fvr.setProcSeq(fvr.getProcSeq() + 1);
//															fvr.setClmNo(omOdDtl.getClmNo());
//															fvr.setOrglOdFvrNo(fvr.getOdFvrNo());
//															fvr.setOdFvrDvsCd("CNCL");
//															fvr.setModDttm(LocalDateTime.now());
//															fvr.setOdFvrNo(null);
//															return omOdFvrDtlRepository.save(fvr)
//																	.flatMap(fvrSave->omOdFvrDtlRepository.update(fvrSave.getAplyQty(),fvrSave.getOdNo(),fvrSave.getOdSeq(),fvrSave.getProcSeq()-1));
//														})
//														.concatMap(fvr->omOdDtlRepository.update(omOdDtl.getCnclQty(),omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq()))
//														.concatMap(update->omOdRepository.update(omOdDtl.getOdNo()))
//														.collectList();
//											});
//								})
//								, OmOdDtl.class)));
//	}
//	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
//	public Mono<ServerResponse> cancelOmOd(ServerRequest request) {
//		return request.bodyToMono(OmOdDtl.class)
//				.flatMap(omOdDtl -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//						.body(BodyInserters.fromProducer(
//								omOdDtlRepository.save(omOdDtl)
//										.flatMap(data->{
//											omOdDtl.setProcSeq(data.getProcSeq()-1);
//											omOdDtl.setCnclQty(data.getOdQty());
//											return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq())
//													.concatMap(fvr->{
//														fvr.setProcSeq(fvr.getProcSeq() + 1);
//														fvr.setClmNo(omOdDtl.getClmNo());
//														fvr.setOrglOdFvrNo(fvr.getOdFvrNo());
//														fvr.setOdFvrDvsCd("CNCL");
//														fvr.setModDttm(LocalDateTime.now());
//														fvr.setOdFvrNo(null);
//														return omOdFvrDtlRepository.save(fvr)
//																.flatMap(fvrSave->omOdFvrDtlRepository.update(fvrSave.getAplyQty(),fvrSave.getOdNo(),fvrSave.getOdSeq(),fvrSave.getProcSeq()-1));
//													})
//													.concatMap(fvr->omOdDtlRepository.update(omOdDtl.getCnclQty(),omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq()))
//													.concatMap(update->omOdRepository.update(omOdDtl.getOdNo()))
//													.collectList();
//										})
//								, OmOdDtl.class)));
//	}


//.flatMap(update->{
//		return Mono.just(omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(),omOdDtl.getOdSeq(),omOdDtl.getProcSeq())
//				.flatMap(fvr->{
//					fvr.setProcSeq(fvr.getProcSeq()+1);
//					fvr.setClmNo("test222");
//					fvr.setOrglOdFvrNo(fvr.getOdFvrNo());
//					fvr.setOdFvrDvsCd("CNCL");
//					fvr.setModDttm(LocalDateTime.now());
//					fvr.setOdFvrNo(null);
//					return omOdFvrDtlRepository.save(fvr);
//				}));
//	})


//	public Mono<ServerResponse> omOdList(ServerRequest request) {
//		return request.bodyToMono(OmOd.class).flatMap(param -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(
//						omOdRepository.findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(param.getOdNo(),param.getMbNo(),param.getOdrNm())
//								.concatMap(data-> Flux.just(data)
//										.zipWith((omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
//														.groupBy(OmOdDtl::getOdSeq)
//														.concatMap(groupedFlux->{
//															return groupedFlux.last().map(d->{
//																System.out.println(d);
//																return d;
//															});
//															 //groupedFlux.last();
//														})
//														.collectList()),
//												(omOd,omOdDtl)->new OmOd(omOd,omOdDtl)))
//						,OmOd.class)));
//
//		//검색어 없이
////		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromProducer(
////						omOdRepository.findAll()
////								.flatMap(data-> Flux.just(data)
////										.zipWith((omOdDtlRepository.findOdNoAll(data.getOdNo()).cache().collectList()),
////												(omOd,omOdDtl)->new OmOd(omOd,omOdDtl)))
////						, OmOd.class));
//	}
//
//	public Mono<ServerResponse> omOdCancelList(ServerRequest request) {
//		return request.bodyToMono(OmOd.class).flatMap(param -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(
//						omOdRepository.findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(param.getOdNo(),param.getMbNo(),param.getOdrNm())
//								.concatMap(data-> Flux.just(data)
//										.zipWith(
//												(omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
//														.groupBy(OmOdDtl::getOdSeq)
//														.concatMap(groupedFlux-> groupedFlux.last())
//														.filter(omOdDtl->omOdDtl.getOdTypCd().equals("20"))
//														.collectList()
//												),
//												(omOd,omOdDtl)->new OmOd(omOd,omOdDtl))
//								)
//						, OmOd.class)));
//	}
//	public Mono<ServerResponse> odPrgsStepCdList(ServerRequest request) {
//		return request.bodyToMono(OmOd.class).flatMap(param -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromProducer(
//						omOdRepository.findByOdNoContainingAndMbNoContainingAndOdrNmContainingOrderByOdNo(param.getOdNo(),param.getMbNo(),param.getOdrNm())
//								.concatMap(data-> Flux.just(data)
//										.zipWith(
//												(omOdDtlRepository.findByOdNoOrderByOdSeqAscProcSeq(data.getOdNo()).cache()
//														.groupBy(OmOdDtl::getOdSeq)
//														.concatMap(groupedFlux-> groupedFlux.last())
//														.filter(omOdDtl->Integer.parseInt(omOdDtl.getOdPrgsStepCd()) >= 2)
//														.collectList()
//												),
//												(omOd,omOdDtl)->new OmOd(omOd,omOdDtl))
//								)
//						, OmOd.class)));
//	}
}
