package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.entity.BusinessTypeEntity;

public interface TypeDao extends CrudRepository<BusinessTypeEntity, String>  {

}
