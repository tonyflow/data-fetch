package hackathon.data.fetch;

import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchConfigurationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ElasticSearchConfigurationProperties.class)
public class DataFetchApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(DataFetchApplication.class, args);
	}
}
