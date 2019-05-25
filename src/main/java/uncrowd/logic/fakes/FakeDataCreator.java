package uncrowd.logic.fakes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.BusinessTypeDao;
import uncrowd.logic.entity.AverageEntity;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.BusinessTypeEntity;
import uncrowd.logic.entity.CrowdHistoryEntity;
import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.logic.entity.OpeningHoursEntity;
import uncrowd.logic.ml.testing.TestSetConverter;

public class FakeDataCreator {
	
	private String[] TYPES_NAMES = {"Toys", "Kids", "Dairy", "Vegetable", "Fruit"};
	
	
	private BusinessEntity[] BUSINESSES = {
			new BusinessEntity("Toys R Us", 
					"Bnei Efraim 200, Tel-Aviv", 
					32.114825, 
					34.816782, 
					0, 
					1, 
					3, 
					false,
					false),
			new BusinessEntity("Mega", 
					"Bnei Efraim 202, Tel-Aviv", 
					32.115618, 
					34.817415,
					// crowd count
					20, 
					// crowd time
					1, 
					// crowd level
					3, 
					true,
					false),
			new BusinessEntity("Shufersal", 
					"Bnei Efraim 210, Tel-Aviv", 
					32.117581, 
					34.817891,
					// crowd count
					0, 
					// crowd time
					1, 
					// crowd level
					1, 
					false,
					true),
			new BusinessEntity("Mcdonalds", 
					"Bnei Efraim 190, Tel-Aviv", 
					32.114263, 
					34.819194,
					// crowd count
					0, 
					// crowd time
					1, 
					// crowd level
					2, 
					false,
					true),
			new BusinessEntity("Aroma", 
					"Kehilat Pozna 2, Tel Aviv", 
					32.114263, 
					34.819194,
					// crowd count
					0, 
					// crowd time
					1, 
					// crowd level
					5, 
					false,
					true),
			new BusinessEntity("Roladin", 
					"Mivtsa Kadesh 13, Tel Aviv", 
					32.113709, 
					34.81452,
					// crowd count
					0, 
					// crowd time
					1, 
					// crowd level
					4, 
					false,
					true)};
	
	private BusinessTypeDao businessTypes;
	private BusinessDao businesses;
	private List<BusinessTypeEntity> businessTypesList;
	private LastDayCrowdFakeCreator lastDayCrowdFakeCreator;
	
	public FakeDataCreator(BusinessTypeDao businessTypes,
							BusinessDao businesses) {
		lastDayCrowdFakeCreator = new LastDayCrowdFakeCreator();
		businessTypesList = new ArrayList<>();
		this.businesses = businesses;
		this.businessTypes = businessTypes;
	}
	
	public void addBusinesses(){
		addTypes();
		
		Set<BusinessTypeEntity> types = new HashSet<>();
		for(BusinessEntity bis: BUSINESSES) {
			types = new HashSet<>();
			for(int i = 0; i < 4; i ++) {
				types.add(businessTypesList.get((int)Math.round(Math.random() * (TYPES_NAMES.length-1))));
			}
			
			if(bis.getIsMLTestBusiness()) {
				bis.setLastDayCrowd(lastDayCrowdTestSet());
				bis.setNeedsExpectedCountUpdate(true);
			}else if(bis.getIsFakeBusiness()){
				bis.setLastDayCrowd(lastDayCrowdFakeCreator.getRandomLastDayCrowd());
				bis.setNeedsExpectedCountUpdate(true);
			}
			
			bis.setTypes(new ArrayList<>(types));
			bis.setOpeningHours(getOpeningHours());
			bis.setAverages(getAverages(bis));
			
			for(AverageEntity avg : bis.getAverages()) {
				avg.setBusiness(bis);
			}
			
			if(bis.getLastDayCrowd() != null) {
				for(LastDayCrowdEntity ldc : bis.getLastDayCrowd()) {
					ldc.setBusiness(bis);
				}
			}
			
			for(OpeningHoursEntity oh : bis.getOpeningHours()) {
				oh.setBusiness(bis);
			}
			
			if(bis.getCrowdHistory() != null) {
				for(CrowdHistoryEntity ch : bis.getCrowdHistory()) {
					ch.setBusiness(bis);
				}
			}
			
			businesses.save(bis);
		}
	}
	
	private void addTypes() {
		for(String typeName : TYPES_NAMES) {
			BusinessTypeEntity type = new BusinessTypeEntity();
			type.setName(typeName);
			this.businessTypes.save(type);
			businessTypesList.add(type);
		}
	}
	
	private List<AverageEntity> getAverages(BusinessEntity businessToGetAverages) {
		List<AverageEntity> averages = new ArrayList<>();
		AverageEntity a;
		
		// Calculating the max count for this business:
		// (for a non fake business it is assumed as 10)
		Integer maxCrowdCount = 10;
		
		if (businessToGetAverages.getIsFakeBusiness() || businessToGetAverages.getIsMLTestBusiness()) {
			for (LastDayCrowdEntity ldc : businessToGetAverages.getLastDayCrowd()) {
				if (ldc.getType() == LastDayCrowdEntity.COSTUMERS_COUNT_TYPE) {
					if (ldc.getCount() > maxCrowdCount) {
						maxCrowdCount = ldc.getCount();
					}
				}
			}
		}
		
		for(int i = 0 ; i < 7; i ++) {
            for(int j = 0 ; j < 24; j ++){
            	a = new AverageEntity();
            	a.setAverage((int)Math.round(Math.random() * (double)maxCrowdCount));
            	a.setDay(i);
            	a.setDateTime(j * 100);
                averages.add(a);
            }
        }
		
		return averages;
	}
	
	private List<OpeningHoursEntity> getOpeningHours() {
		List<OpeningHoursEntity> openingHours = new ArrayList<>();
		
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
		
		return openingHours;
	}
	
	private List<LastDayCrowdEntity> lastDayCrowdTestSet() {
		return (new TestSetConverter()).getTestData(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}

}
