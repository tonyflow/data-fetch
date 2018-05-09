package hackathon.data.fetch.elasticsearch.configuration;

import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchConfigurationProperties;
import hackathon.data.fetch.elasticsearch.configuration.ElasticSearchSettings;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.client.node.NodeClientModule;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

	@Autowired
	ElasticSearchConfigurationProperties properties;
	
	@SuppressWarnings("resource")
	@Bean
	Client client(){
		Settings settings = ImmutableSettings.settingsBuilder()
		.put(ElasticSearchSettings.NUMBER_OF_REPLICAS, properties.getNumberOfReplicas())
		.put(ElasticSearchSettings.NUMBER_OF_SHARDS, properties.getNumberOfShards())
		.put(ElasticSearchSettings.CLUSTER_NAME, properties.getClusterName())
		.put(ElasticSearchSettings.CLUSTER_SNIFF, properties.isClusterSniff())
		.build();
		
		InetSocketTransportAddress inetSocketTransportAddress = new InetSocketTransportAddress(properties.getHost(), properties.getPort());
		TransportClient client = new TransportClient(settings).addTransportAddress(inetSocketTransportAddress);
		
		return client;
			
	}
}
