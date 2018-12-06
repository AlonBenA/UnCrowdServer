package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;
import uncrowd.logic.Entity.BusinessEntity;

public interface BusinessDao extends CrudRepository<BusinessEntity, Long> {

}
