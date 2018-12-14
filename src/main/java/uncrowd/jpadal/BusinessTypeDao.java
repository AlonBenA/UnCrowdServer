package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.entity.BusinessTypeEntity;

public interface BusinessTypeDao extends CrudRepository<BusinessTypeEntity, Long>  {

}
