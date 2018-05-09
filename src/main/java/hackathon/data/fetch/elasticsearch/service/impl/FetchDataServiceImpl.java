package hackathon.data.fetch.elasticsearch.service.impl;

import hackathon.data.fetch.api.dto.UserReceipeRatingDto;
import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchConfigurationProperties;
import hackathon.data.fetch.elasticsearch.service.FetchDataService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FetchDataServiceImpl implements FetchDataService {

	@Autowired
	protected Client esClient;

	@Autowired
	protected ObjectMapper mapper;

	@Value("${application.recommendations-file}")
	protected String fileName;

	@Autowired
	ElasticSearchConfigurationProperties properties;

	List<String> attributes;

	/**
	 * Method will implement a scroll data fetch from elastic search
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void fetch(String index) {

		File file = new File(fileName);

		SearchResponse searchResponse = esClient
				.prepareSearch(properties.getUsersIndex())
				.setTypes(properties.getUserDocumentType())
				.setQuery(
						QueryBuilders.filteredQuery(new MatchAllQueryBuilder(),
								new TermFilterBuilder("processed", false)))
				.setScroll(new TimeValue(60000)).setSize(10000).execute()
				.actionGet();
		

		attributes = attributesFromMapping(index);
		Integer hitNum = searchResponse.getHits().getHits().length;
		searchResponse.getHits().getHits();

		for (SearchHit hit : searchResponse.getHits().getHits()) {
			try {
				FileUtils.writeStringToFile(file, buildLineAndReindex(hit),
						"UTF-8", true);
				System.out.println("Processed 10000");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		hitNum = searchResponse.getHits().getHits().length;
	}

	@SuppressWarnings({ "unchecked" })
	private List<String> attributesFromMapping(String index) {
		MappingMetaData mapping = this.getMapping(index);
		try {
			if (mapping != null) {
				Map<String, Object> actualMappings = mapping.getSourceAsMap();
				List<String> attrs = new ArrayList<>();
				System.out.println(actualMappings.get("properties"));
				Set<String> keySet = mapper.convertValue(
						actualMappings.get("properties"), Map.class).keySet();
				keySet.stream().forEachOrdered(c -> attrs.add(c));
				return attrs;
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Not Necessary yet
	 * 
	 * @param index
	 * @return
	 */
	private MappingMetaData getMapping(String index) {
		ClusterState cs = esClient.admin().cluster().prepareState()
				.setIndices(index).execute().actionGet().getState();
		IndexMetaData imd = cs.getMetaData().index(index);
		// hardcoded value to get
		return imd.getMappings().get("user");
	}

	@SuppressWarnings("unchecked")
	private String buildLineAndReindex(SearchHit hit)
			throws JsonProcessingException, IOException {
		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		Map<String, String> treeOfContents = mapper.readValue(
				hit.getSourceAsString(), Map.class);
		System.out.println(treeOfContents.keySet());
		builder.append(String.valueOf(treeOfContents.get("userId")));
		builder.append(",");
		builder.append(String.valueOf(treeOfContents.get("receipeId")));
		builder.append(",");
		builder.append(String.valueOf(treeOfContents.get("rating")));
		// for(String c : attributes){
		// if (treeOfContents.get(c)!=null) {
		// builder.append(String.valueOf(treeOfContents.get(c)));
		// }
		// if (!c.equals(attributes.get(attributes.size()-1))) {
		// builder.append(",");
		// }
		// }
		builder.append("\n");

		// re-index document as processed
		UserReceipeRatingDto urr = mapper.readValue(hit.getSourceAsString(),
				UserReceipeRatingDto.class);
		urr.setProcessed(true);
		esClient.prepareIndex(properties.getUsersIndex(),
				properties.getUserDocumentType(),
				String.valueOf(urr.getUserId()))
				.setSource(mapper.writeValueAsString(urr)).get();
		return builder.toString();
	}

	@Override
	public String get(String index, String documentType, String id) {
		GetResponse response = esClient.prepareGet(index, documentType, id)
				.execute().actionGet();

		return response.getSourceAsString();
	}

}
