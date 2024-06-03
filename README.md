## READ ME First
- Source url and remote destination URL must be same
  - Its wierd but this is how it works now otherwise it returns 404
  - `.GET("/secured", http("http://localhost:9091/secured"))`
- Sample code 
``` 
    @Bean
	fun securedRoute(): RouterFunction<ServerResponse> {
		return route()
			.GET("/secured", http("http://localhost:9091/secured"))
			.GET("/secured/{segment}", http("http://localhost:9091/secured/"))
			.POST("/secured", http("http://localhost:9091/secured"))
			.before(addRequestHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
			.build() 
```