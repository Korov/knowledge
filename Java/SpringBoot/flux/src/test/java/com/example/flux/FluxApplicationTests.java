package com.example.flux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class FluxApplicationTests {

	@Test
	void contextLoads() {
		AtomicInteger integer = new AtomicInteger(0);
		integer.incrementAndGet();
	}

}
