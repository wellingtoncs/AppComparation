package com.wcs.compare.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wcs.compare.entity.AreaEntity;
import com.wcs.compare.repository.AreaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ComparatorControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	public AreaRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build();
		this.repository.deleteAll();
	}

	@Test
	public void insert() throws Exception {
		left();
		right();
	}
	
	@Test
	public void equal() throws Exception {
		repository.save(new AreaEntity(1l, "d2VsbGluZ3RvbmNz", "d2VsbGluZ3RvbmNz"));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"d2VsbGluZ3RvbmNz\"" + "}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("True")))
				.andReturn();		
	}
	
	@Test
	public void different() throws Exception {
		repository.save(new AreaEntity(1l, "d2VsbGluZ3RvbmNz", "d2VsbGluZ3RvbmNzMTA="));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"d2VsbGluZ3RvbmNz\"" + "}")).andExpect(status().isOk())
		.andExpect(jsonPath("$.message", is("Left Size 16 and Right Size 20")))
		.andReturn();		
	}
	
	private void left() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"d2VsbGluZ3RvbmNz\"" + "}")).andExpect(status().isOk()).andReturn();
		AreaEntity area = repository.findById(1L);
		Assert.assertThat(area.getId(), Matchers.is(1L));
		Assert.assertThat(area.getLeft(), Matchers.is("d2VsbGluZ3RvbmNz"));
		Assert.assertThat(area.getRight(), Matchers.isEmptyOrNullString());
	}
	
	private void right() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"d2VsbGluZ3RvbmNzMTA=\"" + "}")).andExpect(status().isOk()).andReturn();
		AreaEntity area = repository.findById(1L);
		Assert.assertThat(area.getId(), Matchers.is(1L));
		Assert.assertThat(area.getLeft(), Matchers.is("d2VsbGluZ3RvbmNz"));
		Assert.assertThat(area.getRight(), Matchers.is("d2VsbGluZ3RvbmNzMTA="));
	}
}
