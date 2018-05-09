package hackathon.data.fetch.api.dto;

public class ReceipeMappingDto {

	private Long id;
	private String externalId;
	
	public ReceipeMappingDto(Long id, String externalId) {
		super();
		this.id = id;
		this.externalId = externalId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	
	
}
