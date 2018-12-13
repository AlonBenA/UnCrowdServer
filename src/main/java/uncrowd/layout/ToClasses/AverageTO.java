package uncrowd.layout.ToClasses;

import uncrowd.logic.Entity.AverageEntity;

public class AverageTO implements Comparable<AverageTO>{
	
	Integer Day;
	Integer dateTime;
	Integer Average;
	Long businessId;
	
	public int getDay() {
		return Day;
	}

	public void setDay(int day) {
		Day = day;
	}

	public int getDateTime() {
		return dateTime;
	}

	public void setDateTime(int dateTime) {
		this.dateTime = dateTime;
	}

	public int getAverage() {
		return Average;
	}

	public void setAverage(int average) {
		Average = average;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	public AverageTO() {}
	
	public AverageTO(AverageEntity entity) {
		if(entity != null) {
			this.Day = entity.getDay();
			this.dateTime = entity.getDateTime();
			this.Average = entity.getAverage();
			this.businessId = entity.getBusiness().getId();
		}
	}

	@Override
	public int compareTo(AverageTO o) {
		int dayCompareTo = this.Day.compareTo(o.Day);
		int dateTimeCompareTo = this.dateTime.compareTo(o.dateTime);
		if(dayCompareTo == 0) {
			return dateTimeCompareTo; 
		}
		
		return dayCompareTo;
	}

}
