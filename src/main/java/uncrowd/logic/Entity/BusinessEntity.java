package uncrowd.logic.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Business")
public class BusinessEntity {
	Long id;
	String name;
	String address;
	Long latitude;
	Long Longitude;
	Integer currCrowdCount;
	Integer currCrowdLevel;
	
	@Id
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
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	public Long getLongitude() {
		return Longitude;
	}
	public void setLongitude(Long longitude) {
		Longitude = longitude;
	}
	public Integer getCurrCrowdCount() {
		return currCrowdCount;
	}
	public void setCurrCrowdCount(Integer currCrowdCount) {
		this.currCrowdCount = currCrowdCount;
	}
	public Integer getCurrCrowdLevel() {
		return currCrowdLevel;
	}
	public void setCurrCrowdLevel(Integer currCrowdLevel) {
		this.currCrowdLevel = currCrowdLevel;
	}
	
	public BusinessEntity(){
		
	}
}
