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
import uncrowd.logic.Entity.AverageEntity;
import uncrowd.logic.Entity.BusinessEntity;
import uncrowd.logic.Entity.BusinessTypeEntity;
import uncrowd.logic.Entity.CrowdHistoryEntity;
import uncrowd.logic.Entity.LastDayCrowdEntity;
import uncrowd.logic.Entity.OpeningHoursEntity;

@Service
public class jpaClientService implements ClientService {

	private AverageDao averages;
	private BusinessDao businesses;
	private BusinessTypeDao businessTypes;
	private CrowdHistoryDao crowdHistory;
	private LastDayCrowdDao lastDayCrowd;
	private OpeningHoursDao openingHours;
	
	
	@Autowired
	public jpaClientService(AverageDao averages,
								BusinessDao businesses,
								BusinessTypeDao businessTypes,
								CrowdHistoryDao crowdHistory,
								LastDayCrowdDao lastDayCrowd,
								OpeningHoursDao openingHours) {
		super();
		this.averages = averages;
		this.businesses = businesses;
		this.businessTypes = businessTypes;
		this.crowdHistory = crowdHistory;
		this.lastDayCrowd = lastDayCrowd;
		this.openingHours = openingHours;
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
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		
		
		/*.stream() // stream of entities
		.filter(ent->ent.getMoreAttributes().get("abc") != null)
		.filter(ent->(int)ent.getMoreAttributes().get("abc") > value)
		.skip(size*page)
		.limit(size)
		.collect(Collectors.toList());*/
		
		//allList.stream().filter(bus -> bus.getName().toLowerCase().contains(name.toLowerCase()))
		//.filter(predicate)
		return null;
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
		BusinessTypeEntity type = new BusinessTypeEntity();
		type.setName("type2");
		BusinessTypeEntity type2 = new BusinessTypeEntity();
		type2.setName("type3");
		
		List<BusinessTypeEntity> types = new ArrayList<>();
		types.add(type);
		types.add(type2);
		
		this.businessTypes.save(type);
		this.businessTypes.save(type2);
		 
		
		AverageEntity a = new AverageEntity();
		a.setAverage(10);
		a.setDateTime(1700);
		a.setDay(2);
		
		
		AverageEntity a2 = new AverageEntity();
		a2.setAverage(12);
		a2.setDateTime(1600);
		a2.setDay(2);
		
		List<AverageEntity> averages = new ArrayList<>();
		averages.add(a);
		averages.add(a2);
		
		
		LastDayCrowdEntity ldc = new LastDayCrowdEntity();
		ldc.setCount(15);
		ldc.setTimeId(1000);
		ldc.setType(0);
		
		LastDayCrowdEntity ldc2 = new LastDayCrowdEntity();
		ldc2.setCount(32);
		ldc2.setTimeId(1100);
		ldc2.setType(1);
		
		List<LastDayCrowdEntity> lastDayCrowd = new ArrayList<>();
		lastDayCrowd.add(ldc);
		lastDayCrowd.add(ldc2);
		
		
		
		OpeningHoursEntity oh = new OpeningHoursEntity();
		oh.setCloseHour(1600);
		oh.setDay(2);
		oh.setOpenHour(1000);
		
		OpeningHoursEntity oh2 = new OpeningHoursEntity();
		oh2.setCloseHour(1700);
		oh2.setDay(3);
		oh2.setOpenHour(1100);
		
		List<OpeningHoursEntity> openingHours = new ArrayList<>();
		openingHours.add(oh);
		openingHours.add(oh2);

		
		CrowdHistoryEntity ch = new CrowdHistoryEntity();
		ch.setCrowdCount(12);
		ch.setDateTime(1000);
		ch.setDay(1);
		
		CrowdHistoryEntity ch2 = new CrowdHistoryEntity();
		ch2.setCrowdCount(22);
		ch2.setDateTime(1100);
		ch2.setDay(3);
		
		List<CrowdHistoryEntity> crowdHistory = new ArrayList<>();
		crowdHistory.add(ch);
		crowdHistory.add(ch2);
		
		
		
		BusinessEntity b = new BusinessEntity();
		b.setAddress("Address");
		b.setLatitude(32.222);
		b.setLongitude(30.222);
		b.setCurrCrowdCount(5);
		b.setCurrCrowdLevel(5);
		b.setName("AA");
		b.setTypes(types);
		b.setAverages(averages);
		b.setLastDayCrowd(lastDayCrowd);
		b.setOpeningHours(openingHours);
		b.setCrowdHistory(crowdHistory);
		
		ch.setBusiness(b);
		ch2.setBusiness(b);
		
		oh.setBusiness(b);
		oh2.setBusiness(b);
		
		ldc.setBusiness(b);
		ldc2.setBusiness(b);
		
		a.setBusiness(b);
		a2.setBusiness(b);
		
		businesses.save(b);
		
		
	}
}
