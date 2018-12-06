package uncrowd.layout;

import uncrowd.logic.Entity.CrowdHistoryEntity;

public class CrowdHistoryTO {
	Long id;
	Integer dateTime;
	Integer day;
	Integer crowdCount;
	Long businessId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDateTime() {
		return dateTime;
	}

	public void setDateTime(Integer dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getCrowdCount() {
		return crowdCount;
	}

	public void setCrowdCount(Integer crowdCount) {
		this.crowdCount = crowdCount;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public CrowdHistoryTO() {}
	
	public CrowdHistoryTO(CrowdHistoryEntity entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.dateTime = entity.getDateTime();
			this.day = entity.getDay();
			this.crowdCount = entity.getCrowdCount();
			this.businessId = entity.getBusiness().getId();
		}
	}
}
