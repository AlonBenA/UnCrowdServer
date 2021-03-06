package uncrowd.logic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Average")
public class AverageEntity {
	Long id;
	Integer day;
	Integer Average;
	Integer dateTime;
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