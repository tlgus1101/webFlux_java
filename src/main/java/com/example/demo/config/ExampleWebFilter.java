package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class ExampleWebFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		exchange.getResponse()
         .getHeaders().add("web-filter", "web-filter-test");
		//.dofinally 사용하면 후처리도 가능!?
		return exchange.getSession().map(session -> {
			ServerHttpResponse response = exchange.getResponse();
			if(session.getAttributes().get("id") != null) {
				session.getAttributes().put("error", "error");
				response.setStatusCode(HttpStatus.SEE_OTHER);
				response.getHeaders().add(HttpHeaders.LOCATION, "/api/main");
				return response.setComplete();
			}
			response.setStatusCode(HttpStatus.ACCEPTED);
			return chain.filter(exchange);
		}).flatMap(arg->Mono.from(arg));
		
		// TODO Auto-generated method stub
		//return chain.filter(exchange);
	}
	
	@Bean
	CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);
	}
}
