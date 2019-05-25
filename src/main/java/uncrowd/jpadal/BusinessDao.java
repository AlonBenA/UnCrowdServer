package uncrowd.jpadal;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import uncrowd.logic.entity.BusinessEntity;

public interface BusinessDao extends PagingAndSortingRepository<BusinessEntity, Long> {
	List<BusinessEntity> findByIsMLTestBusinessFalseAndIsFakeBusinessFalseOrderById(Pageable pageable);
	List<BusinessEntity> findByNeedsExpectedCountUpdateTrueOrderById();
}
