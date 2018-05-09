package hackathon.data.fetch.tasks;

import javax.annotation.PostConstruct;

import hackathon.data.fetch.elasticsearch.service.FetchDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledFileCreator {

	@Autowired
	FetchDataService service;

	@Value("${application.recommendation-engine-url}")
	String recommendationEngineUrl;

//	@Scheduled(cron="0 0 0/2 1/1 * ? *")
//	@Scheduled(fixedRate=5000)
	public void createRecommenderFile(){
		System.out.println("ETL running...");
		service.fetch("users");
		
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("Refreshing recommendation engine...");
		restTemplate.getForObject(recommendationEngineUrl + "/refresh",
				Void.class);
	}
	
}
