package uncrowd.layout;

import uncrowd.logic.UpdateFromBusiness;

public class UpdateFromBusinessTO {
	
	Integer numberOFPeople;
	Integer numberOFPeopleThatEnter;
	Integer numberOFPeopleThatExit;
	String[] date;
	String confirmation;
	
	public UpdateFromBusinessTO() {

	}
	
	public UpdateFromBusinessTO(UpdateFromBusiness updateFromBusinessEntity) {
		setConfirmation(updateFromBusinessEntity.getConfirmation());
		setDate(updateFromBusinessEntity.getDate());
		setNumberOFPeople(updateFromBusinessEntity.getNumberOFPeople());
		setNumberOFPeopleThatEnter(updateFromBusinessEntity.getNumberOFPeopleThatEnter());
		setNumberOFPeopleThatExit(updateFromBusinessEntity.getNumberOFPeopleThatExit());
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

	public UpdateFromBusiness toEntity()
	{
		UpdateFromBusiness rv = new UpdateFromBusiness();
		rv.setConfirmation(this.confirmation);
		rv.setDate(this.date);
		rv.setNumberOFPeople(this.numberOFPeople);
		rv.setNumberOFPeopleThatEnter(this.numberOFPeopleThatEnter);
		rv.setNumberOFPeopleThatExit(this.numberOFPeopleThatExit);	
		return rv;
	}
	
	

}
