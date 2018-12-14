package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;
import uncrowd.logic.entity.BusinessEntity;

public interface BusinessDao extends CrudRepository<BusinessEntity, Long> {

}
