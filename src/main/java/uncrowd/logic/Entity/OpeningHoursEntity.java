package uncrowd.logic.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OpeningHours")
public class OpeningHoursEntity {

	Long id;
	Integer day;
	Integer businessId;
	Integer open;
	Integer close;
	
	@Id
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getDay() {
		return day;
	}



	public void setDay(Integer day) {
		this.day = day;
	}



	public Integer getBusinessId() {
		return businessId;
	}



	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}



	public Integer getOpen() {
		return open;
	}



	public void setOpen(Integer open) {
		this.open = open;
	}



	public Integer getClose() {
		return close;
	}



	public void setClose(Integer close) {
		this.close = close;
	}



	public OpeningHoursEntity() {
	}
	
	
}
