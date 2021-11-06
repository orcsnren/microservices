package com.microservices.currencyexchangeservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {

	private final Logger logger = LogManager.getLogger(CircuitBreakerController.class);

	@GetMapping("sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "dummyResponse")
//	@CircuitBreaker(name = "default", fallbackMethod = "dummyResponse")
	@RateLimiter(name = "default")
	//10s->10000 calls to this url
	@Bulkhead(name="sample-api")//concurent call limit
	public String api() {
		logger.info("api call");
//		ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url",
//				String.class);
//		return entity.getBody();
		return "sample response";
	}

	public String dummyResponse(Exception ex) {
		return "dummy response";
	}

}
