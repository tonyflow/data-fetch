package hackathon.data.fetch.elasticsearch.service;

import java.util.Map;

public interface FetchDataService {
	
	void fetch(String index);

	String get(String index,String documentType,String id);
}
