package uncrowd.logic.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Average")
public class AverageEntity {
	Long id;
	Long businessId;
	Integer day;
	Integer Average;
	Integer dateTime;
	
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

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getAverage() {
		return Average;
	}

	public void setAverage(Integer average) {
		Average = average;
	}

	public Integer getDateTime() {
		return dateTime;
	}

	public void setDateTime(Integer dateTime) {
		this.dateTime = dateTime;
	}

	public AverageEntity() {	
	}	
}
