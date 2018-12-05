package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.Entity.BusinessTypeEntity;

public interface TypeDao extends CrudRepository<BusinessTypeEntity, String>  {

}
