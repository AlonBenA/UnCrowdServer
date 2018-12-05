package uncrowd.logic.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BusinessToBusinessType")
public class BusinessToBusinessTypeEntity {
	Long id;
	Long typeId;
	Long businessTypeId;
	
	@Id
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getTypeId() {
		return typeId;
	}




	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}




	public Long getBusinessTypeId() {
		return businessTypeId;
	}




	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}




	public BusinessToBusinessTypeEntity() {
	}
}
