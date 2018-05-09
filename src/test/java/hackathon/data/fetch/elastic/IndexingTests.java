package hackathon.data.fetch.elastic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import hackathon.data.fetch.api.dto.UserReceipeRatingDto;
import hackathon.data.fetch.base.AbstractDataFetchTest;

public class IndexingTests extends AbstractDataFetchTest{

	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	WebApplicationContext context;
	
	@Test
	public void testIndex() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/data-fetch/user")
				.content(mapper.writeValueAsString(new UserReceipeRatingDto(1l, 1l, new Double(3.4), "this is a beautiful exteranl id")))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print());
	}
}
