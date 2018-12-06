package uncrowd.layout;

import java.util.ArrayList;
import java.util.List;

import uncrowd.logic.Entity.AverageEntity;
import uncrowd.logic.Entity.BusinessEntity;
import uncrowd.logic.Entity.BusinessTypeEntity;
import uncrowd.logic.Entity.CrowdHistoryEntity;
import uncrowd.logic.Entity.LastDayCrowdEntity;
import uncrowd.logic.Entity.OpeningHoursEntity;

public class BusinessTO {

	Long id;
	String name;
	String address;
	Double lat;
	Double lon;
	Integer crowdLevel;
	Integer numberOfPeople;
	String[]  numberOfPeopleDate;
	Integer HowMuchPeopleAreLikelyToBe;
	String[] HowMuchPeopleAreLikelyToBeDate;
	List<UpdateFromBusinessTO> lastUpdate;
	List<AverageTO> averages;
	List<CrowdHistoryTO> crowdHistory;
	List<LastDayCrowdTO> lastDayCrowd;
	List<OpeningHoursTO> openingHours;
	List<TypeTO> types;
	
	public BusinessTO(BusinessEntity businessEntity) {
		if (businessEntity != null) {
			//TODO: Handle likely to be
			HowMuchPeopleAreLikelyToBe = 1;
			this.id = businessEntity.getId();
			this.name = businessEntity.getName();
			this.address = businessEntity.getAddress();
			this.lat = businessEntity.getLatitude();
			this.lon = businessEntity.getLongitude();
			this.crowdLevel = businessEntity.getCurrCrowdLevel();
			this.numberOfPeople = businessEntity.getCurrCrowdCount();
			
			// TODO: Handle lastUpdate
			
			if(businessEntity.getAverages() != null) {
				averages = new ArrayList<>();
				for(AverageEntity averageEntity: businessEntity.getAverages()) {
					averages.add(new AverageTO(averageEntity));
				}
			}
			
			if(businessEntity.getCrowdHistory() != null) {
				crowdHistory = new ArrayList<>();
				for(CrowdHistoryEntity crowdHistoryEntity: businessEntity.getCrowdHistory()) {
					crowdHistory.add(new CrowdHistoryTO(crowdHistoryEntity));
				}
			}
			
			if(businessEntity.getLastDayCrowd() != null) {
				lastDayCrowd = new ArrayList<>();
				for(LastDayCrowdEntity lastDayCrowdEntity: businessEntity.getLastDayCrowd()) {
					lastDayCrowd.add(new LastDayCrowdTO(lastDayCrowdEntity));
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

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public String[] getNumberOfPeopleDate() {
		return numberOfPeopleDate;
	}

	public void setNumberOfPeopleDate(String[] numberOfPeopleDate) {
		this.numberOfPeopleDate = numberOfPeopleDate;
	}

	public int getHowMuchPeopleAreLikelyToBe() {
		return HowMuchPeopleAreLikelyToBe;
	}

	public void setHowMuchPeopleAreLikelyToBe(int howMuchPeopleAreLikelyToBe) {
		HowMuchPeopleAreLikelyToBe = howMuchPeopleAreLikelyToBe;
	}

	public String[] getHowMuchPeopleAreLikelyToBeDate() {
		return HowMuchPeopleAreLikelyToBeDate;
	}

	public void setHowMuchPeopleAreLikelyToBeDate(String[] howMuchPeopleAreLikelyToBeDate) {
		HowMuchPeopleAreLikelyToBeDate = howMuchPeopleAreLikelyToBeDate;
	}

	public List<UpdateFromBusinessTO> getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(List<UpdateFromBusinessTO> lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<AverageTO> getAverages() {
		return averages;
	}

	public void setAverages(List<AverageTO> averages) {
		this.averages = averages;
	}
	
	
	
}
