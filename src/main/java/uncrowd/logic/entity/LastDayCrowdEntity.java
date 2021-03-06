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
	public static final Integer COSTUMERS_COUNT_TYPE = 2;
	public static final Integer ENTERING_COSTUMERS_TYPE = 1; 
	public static final Integer EXITING_COSTUMERS_TYPE = 0;
	
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
	
	public LastDayCrowdEntity(LastDayCrowdEntity ldc) {
		this.type = ldc.type;
		this.count = ldc.count;
		this.timeId = ldc.timeId;
	}
	
	@Override
	public String toString() {
		String typeString = "";
		if(this.type == COSTUMERS_COUNT_TYPE) {
			typeString = "Count";
		}else if(this.type == ENTERING_COSTUMERS_TYPE) {
			typeString = "Entring";
		}else if(this.type == EXITING_COSTUMERS_TYPE) {
			typeString = "Exiting";
		}

		return this.count + " " + typeString;
	}
}
