package uncrowd.logic.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BusinessType")
public class BusinessTypeEntity {
	
	Long id;
	String name;
	List<BusinessEntity> businesses;
	
	@ManyToMany(mappedBy = "types")
	public List<BusinessEntity> getTypes() {
		return businesses;
	}
	public void setTypes(List<BusinessEntity> businesses) {
		this.businesses = businesses;
	}
	
	public BusinessTypeEntity() {
		
	}

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BusinessTypeEntity) {
			return this.id == ((BusinessTypeEntity)obj).id;
		}
		return false;
	}
}
