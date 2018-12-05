package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;
import uncrowd.logic.Entity.CrowdHistoryEntity;

public interface CrowdHistoryDao extends CrudRepository<CrowdHistoryEntity, Long>  {

}
