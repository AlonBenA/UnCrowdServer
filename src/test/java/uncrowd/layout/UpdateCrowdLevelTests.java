package uncrowd.layout;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import uncrowd.logic.entity.AverageEntity;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.jpa.JpaSchedulerService;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCrowdLevelTests {

	private JpaSchedulerService schduler;
	private BusinessEntity fakeBusiness;
	
	@Before
	public void setUp(){
		schduler = mock(JpaSchedulerService.class);
		Mockito.doCallRealMethod().when(schduler).updateCrowdLevel(any());;
		//when(schduler.updateCrowdLevel(null)).thenCallRealMethod();
		fakeBusiness = new BusinessEntity();
		List<AverageEntity> averages = new ArrayList<>();
		AverageEntity av = new AverageEntity();
		
		// Low
		av.setAverage(10);
		averages.add(av);
		
		// Middle:
		av = new AverageEntity();
		av.setAverage(20);
		averages.add(av);
		av = new AverageEntity();
		av.setAverage(21);
		averages.add(av);
		av = new AverageEntity();
		av.setAverage(10);
		averages.add(av);
		av = new AverageEntity();
		av.setAverage(30);
		averages.add(av);
		
		// High
		av = new AverageEntity();
		av.setAverage(30);
		averages.add(av);
				
		fakeBusiness.setAverages(averages);
	}
	
	@Test
	public void testExactlyLow() {
		fakeBusiness.setCurrCrowdCount(10);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)1, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void testNewLow() {
		fakeBusiness.setCurrCrowdCount(2);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)1, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void test1Star() {
		fakeBusiness.setCurrCrowdCount(12);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)1, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void test2Star() {
		fakeBusiness.setCurrCrowdCount(15);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)2, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void test3Star() {
		fakeBusiness.setCurrCrowdCount(19);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)3, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void test4Star() {
		fakeBusiness.setCurrCrowdCount(23);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)4, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void test5Star() {
		fakeBusiness.setCurrCrowdCount(27);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)5, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void testExactlyHigh() {
		fakeBusiness.setCurrCrowdCount(30);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)5, fakeBusiness.getCurrCrowdLevel());
	}
	
	@Test
	public void testNewHigh() {
		fakeBusiness.setCurrCrowdCount(33);
		schduler.updateCrowdLevel(fakeBusiness);
		assertEquals((Integer)5, fakeBusiness.getCurrCrowdLevel());
	}
}
