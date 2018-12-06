package uncrowd.logic.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OpeningHours")
public class OpeningHoursEntity {

	Long id;
	Integer day;
	Integer openHour;
	Integer closeHour;
    BusinessEntity business;
	
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
    @JoinColumn(name = "businessId")
	public BusinessEntity getBusiness() {
		return business;
	}

	public void setBusiness(BusinessEntity business) {
		this.business = business;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	public Integer getOpenHour() {
		return openHour;
	}

	public void setOpenHour(Integer openHour) {
		this.openHour = openHour;
	}

	public Integer getCloseHour() {
		return closeHour;
	}

	public void setCloseHour(Integer closeHour) {
		this.closeHour = closeHour;
	}

	public OpeningHoursEntity() {
	}
	
	
}
