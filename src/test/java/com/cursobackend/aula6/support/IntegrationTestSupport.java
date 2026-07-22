package com.cursobackend.aula6.support;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestSupport {

	@Autowired
	protected MockMvc mockMvc;
	
	protected String AuthenticaUser() throws Exception {
		
		String registerJson = """ 
				{
					"email":"user@email.com",
					"password":"123456"
				}		
				""";
		
		mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registerJson))
				.andExpect(status().isCreated());
		
		String loginJson = """ 
				{
					"email":"user@email.com",
					"password":"123456"
				}		
				""";
		
		String response = mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		return JsonPath.read(response, "$.token");
	}
	
}
