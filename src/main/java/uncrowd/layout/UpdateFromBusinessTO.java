package uncrowd.layout;

import uncrowd.logic.UpdateFromBusiness;

public class UpdateFromBusinessTO {
	
	Long id;
	Integer numberOFPeople;
	Integer numberOFPeopleThatEnter;
	Integer numberOFPeopleThatExit;
	Integer date;
	String confirmation;
	
	public UpdateFromBusinessTO() {

	}
	
	public UpdateFromBusinessTO(UpdateFromBusiness updateFromBusinessEntity) {
		setConfirmation(updateFromBusinessEntity.getConfirmation());
		setDate(updateFromBusinessEntity.getDate());
		setNumberOFPeople(updateFromBusinessEntity.getNumberOFPeople());
		setNumberOFPeopleThatEnter(updateFromBusinessEntity.getNumberOFPeopleThatEnter());
		setNumberOFPeopleThatExit(updateFromBusinessEntity.getNumberOFPeopleThatExit());
		setId(updateFromBusinessEntity.getId());
	}

	public UpdateFromBusiness toEntity()
	{
		UpdateFromBusiness rv = new UpdateFromBusiness();
		rv.setConfirmation(this.confirmation);
		rv.setDate(this.date);
		rv.setNumberOFPeople(this.numberOFPeople);
		rv.setNumberOFPeopleThatEnter(this.numberOFPeopleThatEnter);
		rv.setNumberOFPeopleThatExit(this.numberOFPeopleThatExit);
		rv.setId(this.id);
		return rv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	
}
