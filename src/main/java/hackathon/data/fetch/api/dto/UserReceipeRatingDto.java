package hackathon.data.fetch.api.dto;

import java.io.Serializable;

public class UserReceipeRatingDto implements Serializable{

	private static final long serialVersionUID = -53631176437779132L;
	
	private Long userId;
	private Long receipeId;
	private Double rating;
	private String externalId;
	private Boolean processed;
	

	public UserReceipeRatingDto() {
		super();
	}


	public UserReceipeRatingDto(Long userId, Long receipeId, Double rating,String externalId) {
		super();
		this.userId = userId;
		this.receipeId = receipeId;
		this.rating = rating;
		this.externalId = externalId;
	}
	
	
	
	
	public UserReceipeRatingDto(Long userId, Long receipeId, Double rating,
			String externalId, Boolean processed) {
		super();
		this.userId = userId;
		this.receipeId = receipeId;
		this.rating = rating;
		this.externalId = externalId;
		this.processed = processed;
	}


	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getReceipeId() {
		return receipeId;
	}
	
	public void setReceipeId(Long receipeId) {
		this.receipeId = receipeId;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public void setRating(Double rating) {
		this.rating = rating;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public Boolean getProcessed() {
		return processed;
	}


	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	
	
}
