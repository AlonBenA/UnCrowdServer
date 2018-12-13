package uncrowd.logic;

public interface BusinessService {
	
	/**
	 * A method to update the business side
	 * the method update the table LastDayCrowd with 3 updates
	 * the first is the number of people that enter the business
	 *  the second is the number of people that exit the business
	 *  and the last is the number of people Who are currently in business
	 *  the method also update the table business, it's update the business number of people in him(currCrowdCount field )
	 *  and the the level of the Crowdedness(currCrowdLevel field)
	 */
	
	public UpdateFromBusiness addNewUpdate(UpdateFromBusiness newUpdate);
	
	public BusinessId getBusinessId(BusinessId businessId);
	
	
}
