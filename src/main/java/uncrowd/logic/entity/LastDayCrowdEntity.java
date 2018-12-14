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
@Table(name = "LastDayCrowd")
public class LastDayCrowdEntity {
	Long id;
	Integer type;
	Integer count;
	Integer timeId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public LastDayCrowdEntity() {
	}
	
	public LastDayCrowdEntity(Long id,	Integer type,
	Integer count,
	Integer timeId,BusinessEntity business) {
		this.id =id;
		this.type =type;
		this.count =count;
		this.timeId =timeId;
		this.business =business;
	}
}
