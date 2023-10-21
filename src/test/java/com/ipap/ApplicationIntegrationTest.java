package com.ipap;

import com.ipap.springsoap.gen.GetCountryRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

	private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@LocalServerPort
	private int port;

	@BeforeEach
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCountryRequest.class));
		marshaller.afterPropertiesSet();
	}

	@Test
	public void whenSendRequest_thenResponseIsNotNull() {
		WebServiceTemplate ws = new WebServiceTemplate(marshaller);
		GetCountryRequest request = new GetCountryRequest();
		request.setName("Spain");

		Object response = ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request);

		assertThat(response).isNotNull();
	}
}
