package de.codeboje.requestlogging.autoconfigure;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.jboss.logging.MDC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import de.codeboje.requestlogging.RequestContextLoggingInterceptor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestConfig.class, webEnvironment=WebEnvironment.MOCK)
@RestClientTest
public class PostProcessingTest {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MockRestServiceServer mockRestServiceServer;

	@Test
	public void whenInit_IntercepterAdded() {
		boolean found = false;
		for (ClientHttpRequestInterceptor interceptor : restTemplate.getInterceptors()) {
			if (interceptor instanceof RequestContextLoggingInterceptor) {
				found = true;
			}
		}

		assertTrue(found);

	}
	
	
	@Test
	public void whenRestRequest_ExpectHeader() {
		mockRestServiceServer.expect(requestTo("http://example.com/test")).andExpect(header("X-REQUEST-ID", is(not(nullValue())) ))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));
		
		restTemplate.getForObject("http://example.com/test", String.class);

		mockRestServiceServer.verify();
	}
	
	@Test
	public void whenRestRequestAndMDC_ExpectHeader() {
		MDC.put("requestId", "1234id");
		mockRestServiceServer.expect(requestTo("http://example.com/test")).andExpect(header("X-REQUEST-ID", is("1234id") ))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));
		
		restTemplate.getForObject("http://example.com/test", String.class);

		mockRestServiceServer.verify();
	}
}

@SpringBootApplication
@Import(RequestLoggingAutoConfiguration.class)
class TestConfig {

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(TestConfig.class, args);
    }
}
