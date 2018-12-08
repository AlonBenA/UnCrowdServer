package uncrowd.layout;

import java.util.ArrayList;
import java.util.List;

import uncrowd.logic.Entity.AverageEntity;
import uncrowd.logic.Entity.BusinessEntity;
import uncrowd.logic.Entity.BusinessTypeEntity;
import uncrowd.logic.Entity.LastDayCrowdEntity;
import uncrowd.logic.Entity.OpeningHoursEntity;

public class BusinessTO {

	Long id;
	String name;
	String address;
	Double lat;
	Double lon;
	Integer crowdLevel;
	Integer crowdCount;
	Integer expectedCrowdCount;
	Integer[] HowMuchPeopleAreLikelyToBeDate;
	List<AverageTO> averages;
	List<CrowdHistoryTO> crowdHistory;
	List<OpeningHoursTO> openingHours;
	List<TypeTO> types;
	
	public BusinessTO(BusinessEntity businessEntity) {
		if (businessEntity != null) {
			//TODO: Handle likely to be
			expectedCrowdCount = 30;
			this.id = businessEntity.getId();
			this.name = businessEntity.getName();
			this.address = businessEntity.getAddress();
			this.lat = businessEntity.getLatitude();
			this.lon = businessEntity.getLongitude();
			this.crowdLevel = businessEntity.getCurrCrowdLevel();
			this.crowdCount = businessEntity.getCurrCrowdCount();
						
			if(businessEntity.getAverages() != null) {
				averages = new ArrayList<>();
				for(AverageEntity averageEntity: businessEntity.getAverages()) {
					averages.add(new AverageTO(averageEntity));
				}
			}
			
			if(businessEntity.getCrowdHistory() != null) {
				crowdHistory = new ArrayList<>();
				
				// Pulling the last few crowd counts from the crowd history
				List<LastDayCrowdEntity> lastDayCrowdList = businessEntity.getLastDayCrowd();
				// TODO: Put this 3 in a const
				List<LastDayCrowdEntity> lastRecords = lastDayCrowdList.subList(lastDayCrowdList.size() - (3 * 3), lastDayCrowdList.size());
				for(LastDayCrowdEntity lastDayCrowd: lastRecords) {
					// TODO: Change this 2 to const
					// Taking only the total count and sending to client 
					// (in order for it to display current trend graph)
					if (lastDayCrowd.getType() == 2) {
						// Converting the LastDay entity to CrowdHistoryTO
						CrowdHistoryTO currCrowdHistory = new CrowdHistoryTO();
						currCrowdHistory.setBusinessId(id);
						currCrowdHistory.setCrowdCount(lastDayCrowd.getCount());
						currCrowdHistory.setDateTime(lastDayCrowd.getTimeId());
						crowdHistory.add(currCrowdHistory);
					}
				}
			}
			
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

	public Integer getExpectedCrowdCount() {
		return expectedCrowdCount;
	}

	public void setExpectedCrowdCount(Integer expectedCrowdCount) {
		this.expectedCrowdCount = expectedCrowdCount;
	}

	public Integer[] getHowMuchPeopleAreLikelyToBeDate() {
		return HowMuchPeopleAreLikelyToBeDate;
	}

	public void setHowMuchPeopleAreLikelyToBeDate(Integer[] howMuchPeopleAreLikelyToBeDate) {
		HowMuchPeopleAreLikelyToBeDate = howMuchPeopleAreLikelyToBeDate;
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
