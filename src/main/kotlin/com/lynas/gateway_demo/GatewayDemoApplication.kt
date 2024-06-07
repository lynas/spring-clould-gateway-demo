package com.lynas.gateway_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse


@SpringBootApplication
class GatewayDemoApplication

@RestController
class DemoController {

	@GetMapping("/hello")
	fun hello(): String {
		return "hello world"
	}
}

fun main(args: Array<String>) {
	runApplication<GatewayDemoApplication>(*args)
}

@Configuration
class ProxyRouterConfig{
	@Bean
	fun openRoute(): RouterFunction<ServerResponse> {
		return route()
			.GET("/open", http("http://localhost:9091/open"))
			.build()
	}

	@Bean
	fun securedRoute(): RouterFunction<ServerResponse> {
		return route()
			.GET("/secured", http("http://localhost:9091"))
            .before(setPath("/secured"))
			.before(getAuthHeader())
			.build()
	}

    @Bean
    fun securedRouteWithPathVariable(): RouterFunction<ServerResponse> {
        return route()
            .GET("/secured/{segment}", http("http://localhost:9091"))
            .before(setPath("/secured/{segment}"))
            .before(getAuthHeader())
            .build()
    }

    @Bean
    fun securedRouteWithPost(): RouterFunction<ServerResponse> {
        return route()
            .POST("/secured", http("http://localhost:9091/secured"))
            .before(getAuthHeader())
            .build()
    }

    @Bean
    fun securedQueryParam(): RouterFunction<ServerResponse> {
        return route()
            .GET("/securedQueryParam", http("http://localhost:9091"))
            .before(setPath("/securedQueryParam"))
            .before(getAuthHeader())
            .build()
    }



    private fun getAuthHeader() = addRequestHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
}

