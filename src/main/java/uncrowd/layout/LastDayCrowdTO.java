package uncrowd.layout;

import uncrowd.logic.Entity.LastDayCrowdEntity;

public class LastDayCrowdTO {
	Long id;
	Integer type;
	Integer count;
	Integer timeId;
	Long businessId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	public LastDayCrowdTO() {}
	
	public LastDayCrowdTO(LastDayCrowdEntity entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.type = entity.getType();
			this.count = entity.getCount();
			this.timeId = entity.getTimeId();
			this.businessId = entity.getBusiness().getId();
		}
	}
}
