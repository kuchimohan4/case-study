package com.cropdeal;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class apigatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayrouter(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("myheader", "my uri"))
						.uri("http://httpbin.org:80")
						)
				.route(p -> p.path("/auth/**")
						.uri("lb://SECURITY-SERVICE"))
				.route(p -> p.path("/profile/**")
						.uri("lb://PROFILE-MANAGEMENT-SERVICE"))
				.route(p -> p.path("/inventry/**")
//						.filters(f -> f.rewritePath(
//								"/currency-conversion-new/(?<segment>.*)",
//								"/currency-conversion-feign/${segment}"))
						.uri("lb://CROPDEAL-INVENTRY-SERVICE"))
				.route(p -> p.path("/review/**")
						.uri("lb://CROPDEAL-INVENTRY-SERVICE"))
				.route(p -> p.path("/order/**")
						.uri("lb://CROPDEAL-PAYMENTORDER-SERVICE"))
				.build();
		
	}

}