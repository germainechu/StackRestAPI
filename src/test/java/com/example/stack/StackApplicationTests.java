package com.example.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;




@SpringBootTest 
public class StackApplicationTests {
	
	WebTestClient testClient;
	
	@BeforeEach
	void contextLoads() {
		testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
	}

	@Test
	void testGetStackSizeWhenEmpty() {
		System.out.println(testClient.get().uri("/stack/size"));

	}

	@Test
	void testGetStackSizeWhenNotEmpty() {

	}

	@Test
	void testPutIntoStack() {

	}

	@Test
	void testMultiplePutOneDelete() {

	}

	@Test
	void testMultiplePutMultipleDelete() {

	}

	@Test
	void testDeleteWhenEmpty_ExceptionHandling() {

	}

}
