package com.example.demo.config;

//import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class ExampleHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

	@Override
	public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> handlerFunction) {

//		if(request.pathVariable("name").equalsIgnoreCase("test")) {
//			System.out.println();
//			return ServerResponse.status(HttpStatus.FORBIDDEN).build();
//		}
		
		return handlerFunction.handle(request);
	}


}
