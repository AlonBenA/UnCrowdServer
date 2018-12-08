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
		
		List<BusinessTypeEntity> types = new ArrayList<>();
		types(types);
		
		List<AverageEntity> averages = new ArrayList<>();
		averages(averages);
		
		List<LastDayCrowdEntity> lastDayCrowd = new ArrayList<>();
		lastDayCrowd(lastDayCrowd);
		
		
		List<OpeningHoursEntity> openingHours = new ArrayList<>();
		openingHours(openingHours);

		
		List<CrowdHistoryEntity> crowdHistory = new ArrayList<>();
		crowdHistory(crowdHistory);
		
		
		BusinessEntity b = new BusinessEntity();
		b.setAddress("Sheshet Hayamim 200, Bnei Brak");
		b.setLatitude(32.114825);
		b.setLongitude(34.816782);
		b.setCurrCrowdCount(20);
		b.setCurrCrowdLevel(4);
		b.setName("Toys R Us");
		b.setTypes(types);
		b.setAverages(averages);
		b.setLastDayCrowd(lastDayCrowd);
		b.setOpeningHours(openingHours);
		b.setCrowdHistory(crowdHistory);
		
		for(AverageEntity avg : averages) {
			avg.setBusiness(b);
		}
		
		for(LastDayCrowdEntity ldc : lastDayCrowd) {
			ldc.setBusiness(b);
		}
		
		for(OpeningHoursEntity oh : openingHours) {
			oh.setBusiness(b);
		}
		
		for(CrowdHistoryEntity ch : crowdHistory) {
			ch.setBusiness(b);
		}
		
		/*ch.setBusiness(b);
		ch2.setBusiness(b);
		
		oh.setBusiness(b);
		oh2.setBusiness(b);
		
		ldc.setBusiness(b);
		ldc2.setBusiness(b);
		
		a.setBusiness(b);
		a2.setBusiness(b);*/
		
		businesses.save(b);	
	}

	private void types(List<BusinessTypeEntity> types) {
		BusinessTypeEntity type = new BusinessTypeEntity();
		type.setName("type2");
		types.add(type);
		this.businessTypes.save(type);
		
		type = new BusinessTypeEntity();
		type.setName("type3");
		types.add(type);
		this.businessTypes.save(type);
	}
	
	private void averages(List<AverageEntity> averages) {
		AverageEntity a;
		
		for(int i = 0 ; i < 7; i ++) {
            for(int j = 0 ; j < 24; j ++){
            	a = new AverageEntity();
            	a.setAverage((int)Math.round(Math.random() * 100.0));
            	a.setDay(i);
            	a.setDateTime(j * 100);
                averages.add(a);
            }
        }
	}
	
	private void crowdHistory(List<CrowdHistoryEntity> crowdHistory) {
		CrowdHistoryEntity ch = new CrowdHistoryEntity();
		ch.setCrowdCount(12);
		ch.setDateTime(1000);
		ch.setDay(1);
		crowdHistory.add(ch);
		
		ch = new CrowdHistoryEntity();
		ch.setCrowdCount(22);
		ch.setDateTime(1100);
		ch.setDay(3);
		crowdHistory.add(ch);
	}
	
	private void lastDayCrowd(List<LastDayCrowdEntity> lastDayCrowd) {
		
		LastDayCrowdEntity ldc = new LastDayCrowdEntity();
		ldc.setCount(15);
		ldc.setTimeId(1000);
		ldc.setType(0);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(32);
		ldc.setTimeId(1000);
		ldc.setType(1);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(0);
		ldc.setTimeId(1000);
		ldc.setType(2);
		lastDayCrowd.add(ldc);
		
		
		
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(15);
		ldc.setTimeId(1100);
		ldc.setType(0);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(32);
		ldc.setTimeId(1100);
		ldc.setType(1);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(1);
		ldc.setTimeId(1100);
		ldc.setType(2);
		lastDayCrowd.add(ldc);
		
		
		
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(15);
		ldc.setTimeId(1200);
		ldc.setType(0);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(32);
		ldc.setTimeId(1200);
		ldc.setType(1);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(4);
		ldc.setTimeId(1200);
		ldc.setType(2);
		lastDayCrowd.add(ldc);
		
		
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(15);
		ldc.setTimeId(1300);
		ldc.setType(0);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(32);
		ldc.setTimeId(1300);
		ldc.setType(1);
		lastDayCrowd.add(ldc);
		
		ldc = new LastDayCrowdEntity();
		ldc.setCount(5);
		ldc.setTimeId(1300);
		ldc.setType(2);
		lastDayCrowd.add(ldc);
	}
	
	private void openingHours(List<OpeningHoursEntity> openingHours) {
		OpeningHoursEntity oh = new OpeningHoursEntity();
		oh.setDay(0);
		oh.setOpenHour(1100);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(1);
		oh.setOpenHour(1000);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(2);
		oh.setOpenHour(900);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(3);
		oh.setOpenHour(800);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(4);
		oh.setOpenHour(700);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(5);
		oh.setOpenHour(600);
		oh.setCloseHour(1700);
		openingHours.add(oh);
		
		oh = new OpeningHoursEntity();
		oh.setDay(6);
		oh.setOpenHour(500);
		oh.setCloseHour(1700);
		openingHours.add(oh);
	}
}