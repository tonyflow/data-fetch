package hackathon.data.fetch.elasticsearch.service.impl;

import org.apache.lucene.search.FilteredQuery;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lucene.search.AndFilter;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.NotFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsFilterBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchConfigurationProperties;
import hackathon.data.fetch.elasticsearch.service.IndexDataService;
import hackathon.data.fetch.elasticsearch.service.QueryDataService;

@Component
public class QueryDataServiceImpl implements QueryDataService{

	@Autowired
	Client esClient;
	
	@Autowired
	ElasticSearchConfigurationProperties properties;
	
	
	@Override
	public String queryForExternal(Long id) {
		TermQueryBuilder q = new TermQueryBuilder("id", id.intValue());
		MatchAllQueryBuilder matchAll = new MatchAllQueryBuilder();
		
		new FilteredQueryBuilder(matchAll, new TermFilterBuilder("id", id));
		
		SearchResponse response = esClient.prepareSearch(properties.getReceipesMappingIndex())
			.setTypes(properties.getReceipeMappingDocumentType())
			.setQuery(QueryBuilders.filteredQuery(new MatchAllQueryBuilder(), new TermFilterBuilder("id", id)))
			.execute()
			.actionGet();
		
		return response.getHits().getHits()[0].getSource().get("externalId").toString();
	}

	@Override
	public void queryForNew() {

		NotFilterBuilder notProcessed = new NotFilterBuilder(new TermsFilterBuilder("processes", true));
		SearchResponse response = esClient.prepareSearch(properties.getUsersIndex())
				.setTypes(properties.getUserDocumentType())
				.setPostFilter(notProcessed)
				.execute()
				.actionGet();
		
	}

	@Override
	public Long queryForId(String externalId) {
		SearchResponse r = esClient.prepareSearch(properties.getReceipesMappingIndex())
			.setTypes(properties.getReceipeMappingDocumentType())
			.setQuery(QueryBuilders.filteredQuery(new MatchAllQueryBuilder(), new TermFilterBuilder("externalId", externalId)))
			.execute()
			.actionGet();
		if (r.getHits().getHits().length==0) {
			return null;
		}
		return Long.valueOf(r.getHits().getHits()[0].getSource().get("id").toString());
	}

}
