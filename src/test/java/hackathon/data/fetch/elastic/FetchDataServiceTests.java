package hackathon.data.fetch.elastic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import hackathon.data.fetch.base.AbstractDataFetchTest;
import hackathon.data.fetch.elasticsearch.service.FetchDataService;

public class FetchDataServiceTests extends AbstractDataFetchTest{

	@Autowired
	FetchDataService service;
	
	@Test
	public void testFetch() throws Exception {
		service.fetch("users");
	}
	
}
