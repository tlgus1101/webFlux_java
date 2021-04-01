package com.example.demo.config;

import com.example.demo.handler.OmOdHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.example.demo.handler.TodoHandler;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Configuration
public class TodoRouter {
	@Bean
	public RouterFunction<ServerResponse> route(TodoHandler handler) {
		return RouterFunctions.route().POST("/api/insert", RequestPredicates.accept(APPLICATION_JSON), handler::insert)
				.POST("/api/delete", RequestPredicates.accept(MediaType.ALL), handler::delete)
				.POST("/api/update", RequestPredicates.accept(APPLICATION_JSON), handler::update)
				.GET("/api/todoList", RequestPredicates.accept(MediaType.ALL), handler::todoList)
				.filter(new ExampleHandlerFilterFunction()).build();
	}

//	@Bean
//	public RouterFunction<ServerResponse> filterFunction(TodoHandler handler) {
//		return RouterFunctions.route()
//				.GET("/api/{name}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::todoList)
//				.filter(new ExampleHandlerFilterFunction()).build();
//	}

	@Bean
	public RouterFunction<ServerResponse> OmOdRoute(OmOdHandler handler) {
		return RouterFunctions.route().POST("/api/omOdList", RequestPredicates.accept(APPLICATION_JSON), handler::omOdList)
				.POST("/api/omOdCancelList", RequestPredicates.accept(APPLICATION_JSON), handler::omOdCancelList)
				.POST("/api/odPrgsStepCdList", RequestPredicates.accept(APPLICATION_JSON), handler::odPrgsStepCdList)
				.POST("/api/omOdInsert", RequestPredicates.accept(APPLICATION_JSON), handler::omOdInsert)
				.POST("/api/cancelOmOd", RequestPredicates.accept(APPLICATION_JSON), handler::cancelOmOd)
				.POST("/api/cancelOmOdList", RequestPredicates.accept(APPLICATION_JSON), handler::cancelOmOdList)
				.filter(new ExampleHandlerFilterFunction()).build();
	}
}
