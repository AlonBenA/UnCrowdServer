package uncrowd.logic.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.AverageDao;
import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.BusinessTypeDao;
import uncrowd.jpadal.CrowdHistoryDao;
import uncrowd.jpadal.LastDayCrowdDao;

import uncrowd.jpadal.OpeningHoursDao;
import uncrowd.logic.ClientService;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.BusinessTypeEntity;
import uncrowd.logic.fakes.FakeDataCreator;

@Service
public class jpaClientService implements ClientService {

	private BusinessDao businesses;
	private BusinessTypeDao businessTypes;	
	
	@Autowired
	public jpaClientService(AverageDao averages,
								BusinessDao businesses,
								BusinessTypeDao businessTypes,
								CrowdHistoryDao crowdHistory,
								LastDayCrowdDao lastDayCrowd,
								OpeningHoursDao openingHours) {
		super();
		this.businesses = businesses;
		this.businessTypes = businessTypes;
	}
	
	@Override
	public List<BusinessEntity> getAllBusinesses(double longitude, double latitude, int size, int page) {
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		return allList;
	}

	@Override
	public List<BusinessEntity> getBusinessesFiltered(double clientLongitude, double clientLatitude, String name,
			List<Long> businessesTypes, int radius, int size, int page) {
		
		System.out.println("*******getBusinessesFiltered: \nclientLongitude = " + clientLongitude + 
				"\nclientLatitude = " + clientLatitude + 
				"\nname = " + name + 
				"\nradius = " + radius);
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		
		List<BusinessEntity> matchingList = new ArrayList<>();
		
		for(BusinessEntity bus: allList){
			// Checking if the current business belongs to one of the types given:
			boolean doesTypeMatch = false;
			if(!businessesTypes.isEmpty()) {
				for(BusinessTypeEntity type : bus.getTypes() ) {
					if(businessesTypes.contains(type.getId())) {
						System.out.println("Found type: " + type.getName());
						doesTypeMatch = true;
						break;
					}
				}
			}else {
				// No types to filter,
				// moving on no meter what
				doesTypeMatch = true;
			}
			
			// Only if there is a matching type
			if(doesTypeMatch) {
				// Checking if the name also is in the given one
				if(name == null || name.equals("") || 
						bus.getName().toLowerCase().contains(name.toLowerCase())) {
					
					System.out.println("Name fits: " + bus.getName());
					
					// Calculating the distance between the client's current location and the buisiness's location
					// (ignoring altitude)
					double distanceFromBusiness = Math.abs(LocationHelpers.distance(clientLatitude, 
							bus.getLatitude(), 
							clientLongitude, 
							bus.getLongitude(), 
							0, 0));
					
					System.out.println("Distance is: " + distanceFromBusiness);
					
					// Checking if the distance between the business and the user is smaller than the radius given
					// (radius unit is KM and distance is M, hence the conversion)
					if(radius <= 0 || distanceFromBusiness <= radius * 1000) {
						matchingList.add(bus);
					}
				}
			}
		}
		
		return matchingList;
	}

	@Override
	public List<BusinessTypeEntity> getBusinessesTypes() {
		List<BusinessTypeEntity> allList = new ArrayList<>();
		
		this.businessTypes.findAll()
			.forEach(allList::add);
		
		return allList;
	}

	@Override
	public BusinessEntity getBusinessUpdate(long businessId) {
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		
		return allList.stream()
				.filter(business -> business.getId() == businessId)
				.findAny()
				.orElse(null);
	}

	@Override
	public void addDeafultValues() {
		FakeDataCreator fakeDataCreator = new FakeDataCreator(this.businessTypes, this.businesses);
		fakeDataCreator.addBusinesses();
	}
}
