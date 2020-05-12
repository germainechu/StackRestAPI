package com.example.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(JUnit4.class)
public class StackApplicationTests {
	HttpUriRequest request; 
	HttpResponse response;

	@BeforeClass
	void contextLoads() throws IOException {
		//subprocess to open port
		Runtime.getRuntime().exec(new String[]{"python", "-m", "SimpleHTTPServer", "8080"});
	}

	@Test
	void testGetStackSize_EmptyHappyPath() throws ClientProtocolException, IOException {
		request = new HttpGet("http://localhost:8080/stack/size");
		response = HttpClientBuilder.create().build().execute(request);
		assertEquals(response.getStatusLine().getStatusCode(),
		(HttpStatus.SC_NOT_FOUND));
	}

	@Test
	void testGetStackSize_CheckIfReturningJSON() throws ClientProtocolException, IOException {
		String jsonMimeType = "text/html";
		request = new HttpGet("http://localhost:8080/stack/size");
		response = HttpClientBuilder.create().build().execute(request);
		String mimeType = ContentType.getLenientOrDefault(response.getEntity()).getMimeType();
		assertEquals(jsonMimeType, mimeType);

	}

	@Test
	void testGetStackSize_JSONPayload() throws ClientProtocolException, IOException {
		request = new HttpPut("http://localhost:8080/stack/test");
		response = HttpClientBuilder.create().build().execute(request);

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
