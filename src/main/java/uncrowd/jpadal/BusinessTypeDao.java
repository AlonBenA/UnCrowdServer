package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.Entity.BusinessTypeEntity;

public interface BusinessTypeDao extends CrudRepository<BusinessTypeEntity, Long>  {

}
