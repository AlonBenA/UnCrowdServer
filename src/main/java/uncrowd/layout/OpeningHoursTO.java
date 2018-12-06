package uncrowd.layout;

import uncrowd.logic.Entity.OpeningHoursEntity;

public class OpeningHoursTO {
	Long id;
	Integer day;
	Integer open;
	Integer close;
	Long businessId;
	
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

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	public OpeningHoursTO() {}
	
	public OpeningHoursTO(OpeningHoursEntity entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.day = entity.getDay();
			this.open = entity.getOpenHour();
			this.close = entity.getCloseHour();
			this.businessId = entity.getBusiness().getId();
		}
	}
    
}
