package hackathon.data.fetch.elasticsearch.service;

import hackathon.data.fetch.api.dto.ReceipeMappingDto;
import hackathon.data.fetch.api.dto.UserReceipeRatingDto;

public interface IndexDataService {
	boolean index(UserReceipeRatingDto urr);
	Long index(ReceipeMappingDto rm);
}
