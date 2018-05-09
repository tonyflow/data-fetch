package hackathon.data.fetch.elasticsearch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:application.yml", ignoreUnknownFields = false, prefix = "application.elasticsearch")
public class ElasticSearchConfigurationProperties {
	

	private String host;

	private Integer port;

	private String clusterName;

	private String usersIndex;

	private String userDocumentType;
	
	private String receipesMappingIndex;
	
	private String receipeMappingDocumentType;

	private String numberOfShards;

	private String numberOfReplicas;

	private boolean clusterSniff;

	public boolean isClusterSniff() {
		return clusterSniff;
	}

	public void setClusterSniff(boolean clusterSniff) {
		this.clusterSniff = clusterSniff;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getNumberOfReplicas() {
		return numberOfReplicas;
	}

	public void setNumberOfReplicas(String numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}

	public String getNumberOfShards() {
		return numberOfShards;
	}

	public void setNumberOfShards(String numberOfShards) {
		this.numberOfShards = numberOfShards;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getUsersIndex() {
		return usersIndex;
	}

	public void setUsersIndex(String usersIndex) {
		this.usersIndex = usersIndex;
	}

	public String getUserDocumentType() {
		return userDocumentType;
	}

	public void setUserDocumentType(String userDocumentType) {
		this.userDocumentType = userDocumentType;
	}

	public String getReceipesMappingIndex() {
		return receipesMappingIndex;
	}

	public void setReceipesMappingIndex(String receipesMappingIndex) {
		this.receipesMappingIndex = receipesMappingIndex;
	}

	public String getReceipeMappingDocumentType() {
		return receipeMappingDocumentType;
	}

	public void setReceipeMappingDocumentType(String receipesMappingDocumentType) {
		this.receipeMappingDocumentType = receipesMappingDocumentType;
	}

	
	
}
