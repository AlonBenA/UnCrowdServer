package uncrowd.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.BusinessTypeEntity;

public class BusinessAlternativesCalculator {
	private List<BusinessEntity> allBusinesses;
	private long idForBusToCalculateAlternatives;
	
	public BusinessAlternativesCalculator(List<BusinessEntity> allBusinesses, long idForBusToCalculateAlternatives) {
		this.allBusinesses = allBusinesses;
		this.idForBusToCalculateAlternatives = idForBusToCalculateAlternatives;
	}

	public List<BusinessEntity> calculateAlternatives(){
		// Finding the original business
		BusinessEntity originalBusiness = null;
		for (BusinessEntity business : allBusinesses) {
			if(business.getId() == idForBusToCalculateAlternatives) {
				originalBusiness = business;
			}
		}
		List<BusinessTypeEntity> originalBusinessTypes = originalBusiness.getTypes();
		
		List<BusinessWithMatchGrade> alternatives = new ArrayList<>();
		
		// Going over all businesses
		for (BusinessEntity business : allBusinesses) {
			// Not including the original business
			if(business.getId() != idForBusToCalculateAlternatives) {
				
				// Getting a replication of the types list of the current business
				List<BusinessTypeEntity> currBusTypes = new ArrayList<BusinessTypeEntity>(business.getTypes());
				// Removing all the types that also belongs to the original one
				currBusTypes.removeAll(originalBusinessTypes);
				// If the original types list of the current business is NOT the same as the 
				// list which we removed types from than the original business and the current one has 
				// parallel types
				if(currBusTypes.size() != business.getTypes().size()) {
					// Calculating the number of matching business types for the original business and the current
					int matchingTypes = business.getTypes().size() - currBusTypes.size();
					
					// When the businesses names are similar, there is a probability that they are more relevant
					if(business.getName().contains(originalBusiness.getName())) {
						// The match grade is the number of types that are the same as the original business
						alternatives.add(new BusinessWithMatchGrade(matchingTypes + 1, business));
					}else {
						// The match grade is the number of types that are the same as the original business
						alternatives.add(new BusinessWithMatchGrade(matchingTypes, business));
					}
				}
			}
		}
		
		// Sorting the alternatives by matching grade
		Collections.sort(alternatives);
		
		// Creating a list of businesses entities
		List<BusinessEntity> alternativesAsEntities = new ArrayList<BusinessEntity>();
		for(BusinessWithMatchGrade businessWithGrade : alternatives) {
			alternativesAsEntities.add(businessWithGrade.business);
		}
		
		return alternativesAsEntities;
	}
	
	private class BusinessWithMatchGrade implements Comparable<BusinessWithMatchGrade>{
		private int matchGrade;
		private BusinessEntity business;
		
		BusinessWithMatchGrade(int matchGrade,
								BusinessEntity business){
			this.matchGrade = matchGrade;
			this.business = business;
		}
		
		@Override
		public int compareTo(BusinessWithMatchGrade o) {
			return o.matchGrade - this.matchGrade;
		}
	}
}
