package uncrowd.layout.to;

import uncrowd.logic.BusinessId;

public class BusinessIdTO {
	
	long id;
	
	public BusinessIdTO() {
		id = 3;
	}
	
	public BusinessIdTO(BusinessId BusinessId) {
		id = BusinessId.getId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public BusinessId toBusinessId(String ip)
	{
		BusinessId BusinessId = new BusinessId();
		
		BusinessId.setId(this.id);
		BusinessId.setIp(ip);
		
		return BusinessId;
	}
	

}
