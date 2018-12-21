package uncrowd.logic.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "business")
public class BusinessEntity {
	Long id;
	String name;
	String address;
	Double latitude;
	Double Longitude;
	Integer currCrowdCount;
	Integer currCrowdTime;
	Integer currCrowdLevel;
	// The expected costumers count at the next time unit
	// (Calculated via machine learning algorithm)
	Integer expectedCrowdCount;
	// The time the current expected count is for
	Integer expectedCountTime;
	// Indicating if we need to calculate the expected count cause there was an actual count update
	Boolean needsExpectedCountUpdate;
	// Indicating if we need to calculate the expected count cause there was an actual count update
	Boolean isMLTestBusiness;
	List<AverageEntity> averages;
	List<CrowdHistoryEntity> crowdHistory;
	List<LastDayCrowdEntity> lastDayCrowd;
	List<OpeningHoursEntity> openingHours;
	List<BusinessTypeEntity> types;
	
	
	@ManyToMany
    @JoinTable(name = "business_to_business_type",
    joinColumns = { @JoinColumn(name = "business_id") },
    inverseJoinColumns = { @JoinColumn(name = "business_type_id") })
	public List<BusinessTypeEntity> getTypes() {
		return types;
	}
	public void setTypes(List<BusinessTypeEntity> types) {
		this.types = types;
	}
	
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
	public Integer getCurrCrowdCount() {
		return currCrowdCount;
	}
	public void setCurrCrowdCount(Integer currCrowdCount) {
		this.currCrowdCount = currCrowdCount;
	}
	public Integer getCurrCrowdTime() {
		return currCrowdTime;
	}
	public void setCurrCrowdTime(Integer currCrowdTime) {
		this.currCrowdTime = currCrowdTime;
	}
	public Integer getCurrCrowdLevel() {
		return currCrowdLevel;
	}
	public void setCurrCrowdLevel(Integer currCrowdLevel) {
		this.currCrowdLevel = currCrowdLevel;
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
	public Boolean getNeedsExpectedCountUpdate() {
		return needsExpectedCountUpdate;
	}
	public void setNeedsExpectedCountUpdate(Boolean needsExpectedCountUpdate) {
		this.needsExpectedCountUpdate = needsExpectedCountUpdate;
	}
	
	public Boolean getIsMLTestBusiness() {
		return isMLTestBusiness;
	}
	public void setIsMLTestBusiness(Boolean isMLTestBusiness) {
		this.isMLTestBusiness = isMLTestBusiness;
	}
	
	@OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    public List<AverageEntity> getAverages() {
        return averages;
    }

    public void setAverages(List<AverageEntity> averages) {
        this.averages = averages;
    }
	
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    public List<CrowdHistoryEntity> getCrowdHistory() {
        return crowdHistory;
    }

    public void setCrowdHistory(List<CrowdHistoryEntity> crowdHistory) {
        this.crowdHistory = crowdHistory;
    }

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    public List<LastDayCrowdEntity> getLastDayCrowd() {
        return lastDayCrowd;
    }

    public void setLastDayCrowd(List<LastDayCrowdEntity> lastDayCrowd) {
        this.lastDayCrowd = lastDayCrowd;
    }

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    public List<OpeningHoursEntity> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHoursEntity> openingHours) {
        this.openingHours = openingHours;
    }
    
	public BusinessEntity(){
		this.needsExpectedCountUpdate = false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BusinessEntity) {
			return ((BusinessEntity) obj).id == this.id;
		}
		return false;
	}
}
