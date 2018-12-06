package uncrowd.jpadal;

import org.springframework.context.support.StaticApplicationContext;
import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.Entity.LastDayCrowdEntity;

public interface LastDayCrowdDao extends CrudRepository<LastDayCrowdEntity, Long>  {
	
	static int ENTER_TYPE=0;
	static int EXIT_TYPE=0;

}
