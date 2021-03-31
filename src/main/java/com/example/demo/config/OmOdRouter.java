package com.example.demo.config;

import com.example.demo.handler.OmOdHandler;
import com.example.demo.handler.TodoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@Configuration
public class OmOdRouter {
	//@Bean
//	public RouterFunction<ServerResponse> route(OmOdHandler handler) {
//		return RouterFunctions.route().POST("/api/omOdList", RequestPredicates.accept(APPLICATION_JSON), handler::omOdList)
//				.filter(new ExampleHandlerFilterFunction()).build();
//	}
}
