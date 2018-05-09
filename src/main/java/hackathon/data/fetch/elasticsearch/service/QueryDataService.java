package hackathon.data.fetch.elasticsearch.service;

public interface QueryDataService {

	Long queryForId(String externalId);
	
	String queryForExternal(Long id);
	
	void queryForNew();
	
}
