package uncrowd.layout;

public class BusinessTO {

	String id;
	String name;
	String address;
	String[] workingHours;
	int[] types;
	double lat;
	double lon;
	int crowdLevel;
	int numberOfPeople;
	String[]  numberOfPeopleDate;
	int HowMuchPeopleAreLikelyToBe;
	String[] HowMuchPeopleAreLikelyToBeDate;
	UpdateFromBusinessTO[] lastUpdate;
	AverageTO[] Averages;
	
	public BusinessTO() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String[] getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String[] workingHours) {
		this.workingHours = workingHours;
	}

	public int[] getTypes() {
		return types;
	}

	public void setTypes(int[] types) {
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

	public UpdateFromBusinessTO[] getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(UpdateFromBusinessTO[] lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public AverageTO[] getAverages() {
		return Averages;
	}

	public void setAverages(AverageTO[] averages) {
		Averages = averages;
	}
	
	
	
}
