package com.springboot.keycloak.junit5.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.springboot.keycloak.junit5.configuration.CustomKeycloakSpringBootConfigResolver;
import com.springboot.keycloak.junit5.configuration.KeycloakSecurityConfig;

@WebMvcTest(SpringSessionController.class)
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
@ContextConfiguration(classes = { SpringSessionController.class, KeycloakSecurityConfig.class,
		CustomKeycloakSpringBootConfigResolver.class })
@WithCustomKeycloackAuth("user")
public class SpringSessionControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testRohit() throws Exception {
		mockMvc.perform(get("/rohit/get").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@WithCustomKeycloackAuth("usera")
	public void testRohitForbiden() throws Exception {
		mockMvc.perform(get("/rohit/get").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void putMessage() throws Exception {
		EditDevelopedProductDto edit = new EditDevelopedProductDto("id1", "iconName2", "name3");
		mockMvc.perform(put("/rohit/put/{id}", 123).param("id", edit.getId()).param("iconName", edit.getIconName())
				.param("name", edit.getName()).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void persistMessage() throws Exception {
		mockMvc.perform(
				post("/rohit/persistMessage").param("msg", "message").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isFound());
	}

	@Test
	public void destroySession() throws Exception {
		mockMvc.perform(post("/rohit/destroy").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andDo(print())
				.andExpect(status().isFound());
	}

	@Test
	public void deleteMessage() throws Exception {
		List<String> msgs = new ArrayList<>();
		msgs.add("messages");
		mockMvc.perform(delete("/rohit/deleteMessage").sessionAttr("MY_SESSION_MESSAGES", msgs).param("msg", "message")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")).andDo(print()).andExpect(status().isFound());
	}

	@Test
	public void testHello() throws Exception {
		mockMvc.perform(get("/hello").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
}