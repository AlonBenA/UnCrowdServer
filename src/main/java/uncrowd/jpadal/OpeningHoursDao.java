package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;
import uncrowd.logic.Entity.OpeningHoursEntity;

public interface OpeningHoursDao extends CrudRepository<OpeningHoursEntity, Long>  {

}
