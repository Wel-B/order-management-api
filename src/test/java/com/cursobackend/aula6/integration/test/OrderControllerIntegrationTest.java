package com.cursobackend.aula6.integration.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.cursobackend.aula6.support.IntegrationTestSupport;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerIntegrationTest extends IntegrationTestSupport {
	
	@Test
	void shouldCreateOrder() throws Exception {
		
		String token = AuthenticaUser();
		
		String json = """ 
				
				{"amount": 500}
				
				""";
		
		String response = mockMvc.perform(post("/orders")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Number idNumber = JsonPath.read(response, "$.id");
		
		Long id = idNumber.longValue();
		  
		mockMvc.perform(patch("/orders/" + id + "/cancel")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").exists());
	}
	
}
