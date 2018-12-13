package uncrowd.layout.ToClasses;

import uncrowd.logic.Entity.BusinessTypeEntity;

public class TypeTO {
	
	Long id;
	String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public TypeTO(){}
	
	public TypeTO(BusinessTypeEntity entity){
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
		}
	}
}
