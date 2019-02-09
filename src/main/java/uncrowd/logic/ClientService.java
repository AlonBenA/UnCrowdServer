package uncrowd.logic;

import java.util.List;

import uncrowd.logic.entity.*;

public interface ClientService {
	public List<BusinessEntity> getAllBusinesses(double longitude, double latitude, int size, int page);
	
	public List<BusinessEntity> getBusinessesFiltered(double clientLongitude, 
			double clientLatitude,
			String name, 
			List<Long> businessesTypes,
			int radius,
			int size, int page);
	
	public List<BusinessTypeEntity> getBusinessesTypes();
	
	public BusinessEntity getBusinessUpdate(long businessId);
	
	public List<BusinessEntity> getAlternatives(long businessId);
	
	public void addDeafultValues();
}
