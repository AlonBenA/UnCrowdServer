package uncrowd.layout.to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import uncrowd.logic.entity.AverageEntity;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.BusinessTypeEntity;
import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.logic.entity.OpeningHoursEntity;
import uncrowd.utils.TimeUtils;

public class BusinessTO {

	Long id;
	String name;
	String address;
	Double lat;
	Double lon;
	Integer crowdLevel;
	Integer crowdCount;
	// The time the last update of crowd count was received
	Integer crowdCountTime;
	// The expected costumers count at the next time unit
	// (Calculated via machine learning algorithm)
	Integer expectedCrowdCount;
	// The time the current expected count is for
	Integer expectedCountTime;
	List<AverageTO> averages;
	List<CrowdHistoryTO> crowdHistory;
	List<OpeningHoursTO> openingHours;
	List<TypeTO> types;
	
	public BusinessTO(BusinessEntity businessEntity) {
		if (businessEntity != null) {
			this.expectedCrowdCount = businessEntity.getExpectedCrowdCount();
			this.expectedCountTime = businessEntity.getExpectedCountTime();
			this.id = businessEntity.getId();
			this.name = businessEntity.getName();
			this.address = businessEntity.getAddress();
			this.lat = businessEntity.getLatitude();
			this.lon = businessEntity.getLongitude();
			this.crowdLevel = businessEntity.getCurrCrowdLevel();
			this.crowdCount = businessEntity.getCurrCrowdCount();
			this.crowdCountTime = businessEntity.getCurrCrowdTime();
			
			if(businessEntity.getAverages() != null) {
				averages = new ArrayList<>();
				for(AverageEntity averageEntity: businessEntity.getAverages()) {
					averages.add(new AverageTO(averageEntity));
				}
			}
			
			pullCrowdHistoryFromLastDay(businessEntity.getLastDayCrowd());
			
			if(businessEntity.getTypes() != null) {
				types = new ArrayList<>();
				for(BusinessTypeEntity typeEntity: businessEntity.getTypes()) {
					types.add(new TypeTO(typeEntity));
				}
			}
			
			if(businessEntity.getOpeningHours() != null) {
				openingHours = new ArrayList<>();
				for(OpeningHoursEntity openingHoursEntity: businessEntity.getOpeningHours()) {
					openingHours.add(new OpeningHoursTO(openingHoursEntity));
				}
				// Sorting the hours by day
				openingHours.sort(null);
			}
		}
	}

	private void pullCrowdHistoryFromLastDay(List<LastDayCrowdEntity> lastDayCrowdList) {
		crowdHistory = new ArrayList<>();
		
		if(lastDayCrowdList != null && lastDayCrowdList.size() > 0) {
			// Generate an iterator. Start just after the last element.
			ListIterator<LastDayCrowdEntity> li = lastDayCrowdList.listIterator(lastDayCrowdList.size());
	
			int currTime = 
					TimeUtils.getCurrTime();

			// Iterate in reverse.
			while(li.hasPrevious()) {
				LastDayCrowdEntity currEntity = li.previous();
				// Checking if the record is earlier than now
				if(currEntity.getType() == LastDayCrowdEntity.COSTUMERS_COUNT_TYPE && 
						currEntity.getTimeId() <= currTime) {
					// Converting the LastDay entity to CrowdHistoryTO
					CrowdHistoryTO currCrowdHistory = new CrowdHistoryTO();
					currCrowdHistory.setBusinessId(id);
					currCrowdHistory.setCrowdCount(currEntity.getCount());
					currCrowdHistory.setDateTime(currEntity.getTimeId());
					crowdHistory.add(currCrowdHistory);
				}
			}
			
			// TODO: Put this 3 in a const
			// Taking only the last 3 records back
			if(crowdHistory.size() >= 3){
				crowdHistory = crowdHistory.subList(0, 3);
			}
			
			// Because we traversed the list from the end, the order is reverse (latest time is first)
			Collections.reverse(crowdHistory);
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<OpeningHoursTO> getWorkingHours() {
		return this.openingHours;
	}

	public void setWorkingHours(List<OpeningHoursTO> openingHours) {
		this.openingHours = openingHours;
	}

	public List<TypeTO> getTypes() {
		return types;
	}

	public void setTypes(List<TypeTO> types) {
		this.types = types;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getCrowdLevel() {
		return crowdLevel;
	}

	public void setCrowdLevel(int crowdLevel) {
		this.crowdLevel = crowdLevel;
	}

	public Integer getCrowdCount() {
		return crowdCount;
	}

	public void setCrowdCount(Integer crowdCount) {
		this.crowdCount = crowdCount;
	}
	
	public Integer getCrowdCountTime() {
		return crowdCountTime;
	}

	public void setCrowdCountTime(Integer crowdCountTime) {
		this.crowdCountTime = crowdCountTime;
	}

	public Integer getExpectedCrowdCount() {
		return expectedCrowdCount;
	}

	public void setExpectedCrowdCount(Integer expectedCrowdCount) {
		this.expectedCrowdCount = expectedCrowdCount;
	}
	
	public Integer getExpectedCountTime() {
		return expectedCountTime;
	}

	public void setExpectedCountTime(Integer expectedCountTime) {
		this.expectedCountTime = expectedCountTime;
	}

	public List<AverageTO> getAverages() {
		return averages;
	}

	public void setAverages(List<AverageTO> averages) {
		this.averages = averages;
	}

	public List<CrowdHistoryTO> getCrowdHistory() {
		return crowdHistory;
	}

	public void setCrowdHistory(List<CrowdHistoryTO> crowdHistory) {
		this.crowdHistory = crowdHistory;
	}
}
