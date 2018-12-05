package uncrowd.logic.Entity;


//Not a Table and just for convenience
public class UpdateFromBusinessEntity {
	
	Integer numberOFPeople;
	Integer numberOFPeopleThatEnter;
	Integer numberOFPeopleThatExit;
	String[] date;
	String confirmation;
	
	
	public UpdateFromBusinessEntity() {
		
	}
	
	
	public Integer getNumberOFPeople() {
		return numberOFPeople;
	}
	public void setNumberOFPeople(Integer numberOFPeople) {
		this.numberOFPeople = numberOFPeople;
	}
	public Integer getNumberOFPeopleThatEnter() {
		return numberOFPeopleThatEnter;
	}
	public void setNumberOFPeopleThatEnter(Integer numberOFPeopleThatEnter) {
		this.numberOFPeopleThatEnter = numberOFPeopleThatEnter;
	}
	public Integer getNumberOFPeopleThatExit() {
		return numberOFPeopleThatExit;
	}
	public void setNumberOFPeopleThatExit(Integer numberOFPeopleThatExit) {
		this.numberOFPeopleThatExit = numberOFPeopleThatExit;
	}


	public String[] getDate() {
		return date;
	}


	public void setDate(String[] date) {
		this.date = date;
	}


	public String getConfirmation() {
		return confirmation;
	}


	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	
	
	
	
	

}
