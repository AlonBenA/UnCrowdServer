package uncrowd.logic.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CrowdHistory")
public class CrowdHistoryEntity {
	Long id;
	Long businessId;
	Integer dateTime;
	Integer day;
	Integer crowdCount;
	
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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

	public CrowdHistoryEntity() {
	}
}
