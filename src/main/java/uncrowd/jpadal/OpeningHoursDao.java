package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;
import uncrowd.logic.entity.OpeningHoursEntity;

public interface OpeningHoursDao extends CrudRepository<OpeningHoursEntity, Long>  {

}
