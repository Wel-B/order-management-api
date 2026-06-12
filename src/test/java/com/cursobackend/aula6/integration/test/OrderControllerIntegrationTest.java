package com.cursobackend.aula6.integration.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void shouldCreateOrder() throws Exception {
		
		String json = """ 
				
				{"amount": "500"}
				
				""";
		
		String response = mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse().getContentAsString();
		
		Number idNumber = JsonPath.read(response, "$.id");
		
		Long id = idNumber.longValue();
		  
		mockMvc.perform(post("/orders/" + id + "/analyze"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").exists());
		
		/* Apenas criação
		mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value("REQUESTED"));
		/*
	
		/* Criação e anlize
		String response = mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse().getContentAsString();
		  
		Number idNumber = JsonPath.read(response, "$.id");
		
		Long id = idNumber.longValue();
		  
		mockMvc.perform(post("/orders/" + id + "/analyze"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").exists());
		*/
	
	}
	
}
