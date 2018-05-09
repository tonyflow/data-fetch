package hackathon.data.fetch.elasticsearch.service.impl;

import java.util.UUID;

import hackathon.data.fetch.api.dto.ReceipeMappingDto;
import hackathon.data.fetch.api.dto.UserReceipeRatingDto;
import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchConfigurationProperties;
import hackathon.data.fetch.elasticsearch.service.IndexDataService;
import hackathon.data.fetch.exceptions.DocumentIndexingException;
import hackathon.data.fetch.exceptions.DocumentSerializationException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class IndexDataServiceImpl implements IndexDataService{

	@Autowired
	Client esClient;
	
	@Autowired
	ElasticSearchConfigurationProperties properties;
	
	Logger logger = LoggerFactory.getLogger(IndexDataServiceImpl.class);
	
	@Autowired
	ObjectMapper mapper;
	
	private static Long ordinal=(long)10000;
	
	@Override
	public boolean index(UserReceipeRatingDto urr) {
		try {
			urr.setProcessed(false);
			Long receipeId = index(new ReceipeMappingDto(urr.getReceipeId(), urr.getExternalId()));
			urr.setReceipeId(receipeId);
			IndexResponse response = esClient.prepareIndex(properties.getUsersIndex()
					, properties.getUserDocumentType()
					,String.valueOf(urr.getUserId())+"-"+String.valueOf(receipeId))
				.setSource(mapper.writeValueAsString(urr))
				.get();
			
			
			if (response.getId()==null && receipeId!=null) {
				throw new DocumentIndexingException("Could not index document. Check ES server");
			}
			return true;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new DocumentSerializationException("Could not serialize UserReceipeRating Dto on ES indexing");
		}
	}

	@Override
	public Long index(ReceipeMappingDto rm) {
		IndexResponse response;
		try {
			if (rm.getId()==null) {
				rm.setId(ordinal++);
				response = esClient.prepareIndex(properties.getReceipesMappingIndex(), properties.getReceipeMappingDocumentType())
						.setSource(mapper.writeValueAsString(rm))
						.get();
			} else{
				response = esClient.prepareIndex(properties.getReceipesMappingIndex(), properties.getReceipeMappingDocumentType(),String.valueOf(rm.getId()))
						.setSource(mapper.writeValueAsString(rm))
						.get();
			}
			
			if (response.getId()==null) {
				throw new DocumentIndexingException("Could not index document. Check ES server");
			}
			return rm.getId();
		} catch (ElasticsearchException | JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
