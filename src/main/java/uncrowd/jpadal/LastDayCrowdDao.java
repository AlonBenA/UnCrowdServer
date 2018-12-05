package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.Entity.LastDayCrowdEntity;

public interface LastDayCrowdDao extends CrudRepository<LastDayCrowdEntity, Long>  {

}
