package com.example.stack;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StackApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testStackApplication() throws Exception {

		//GET returns 0 on empty stack
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":0}");

		//Mutliple PUT, check if GET returns correctly
		this.restTemplate.put("http://localhost:" + port + "/stack/test", String.class);
		this.restTemplate.put("http://localhost:" + port + "/stack/test", String.class);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":2}");

		//Multiple PUT then DELETE, check if GET returns correctly
		this.restTemplate.put("http://localhost:" + port + "/stack/test", String.class);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":3}");
		this.restTemplate.put("http://localhost:" + port + "/stack/test", String.class);
		this.restTemplate.put("http://localhost:" + port + "/stack/test", String.class);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":5}");
		this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":4}");

		// Should not catch exception when DELETE is called on non-empty stack
		try {
			assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":4}");
			this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
			this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
			this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
			assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":1}");
			this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
			assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":0}");
			
		} catch (RuntimeException e) {
			fail("Incorrectly caught ResponseStatusException");
		}

		//Exception handling for when DELETE is called on an empty stack
		// try {
		// 	assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stack/size", String.class)).isEqualTo("{\"size\":0}");
		// 	this.restTemplate.delete("http://localhost:" + port + "/stack/pop", String.class);
		// 	fail("Failed to catch ResponseStatusException");
		// } catch (ResponseStatusException e) {
		// 	e.printStackTrace();

		// }
	}
}
