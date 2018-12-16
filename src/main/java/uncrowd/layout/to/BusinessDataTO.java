package uncrowd.layout.to;

import uncrowd.logic.BusinessData;

public class BusinessDataTO {
	
	long id;
	Integer time;
	
	public BusinessDataTO() {
		this.id = 3;
		this.time = 5;
	}
	
	public BusinessDataTO(BusinessData Data) {
		id = Data.getId();
		this.time = Data.getTime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
		
	public Integer getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BusinessData toBusinessData(String ip)
	{
		BusinessData Data = new BusinessData();
		
		Data.setId(this.id);
		Data.setTime(this.time);
		Data.setIp(ip);
		
		return Data;
	}
	

}
