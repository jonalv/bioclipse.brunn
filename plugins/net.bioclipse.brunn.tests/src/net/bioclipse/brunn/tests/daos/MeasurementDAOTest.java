package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IMeasurementDAO;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.WorkList;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>CellOriginDAOTest</code>
 * 
 * @author jonathan
 *
 */
public class MeasurementDAOTest extends AbstractGenericDAOTest {

	public MeasurementDAOTest() {
		super("measurementDAO");
	}
	
	public IMeasurementDAO getDAO(){
		return (IMeasurementDAO)dao;
	}

	@Test
	public void testDelete() {
		
		double[] data = {0.2, 0.3};
		Measurement measurement = new Measurement(tester, 
				                                  "measurement", 
				                                  new WorkList(tester, "worklist", sampleContainer), 
				                                  instrument,
				                                  resultType);
		measurement.getResults().add( new Result(tester, "result", data , 0) );
		
		assertFalse(measurement.isDeleted());
		getDAO().save(measurement);
		
		session.flush();
		session.clear();
		
		getDAO().delete(measurement);
		
		session.flush();
		session.clear();
		
		Measurement deleted = getDAO().getById(new Long(measurement.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.MeasurementDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		double[] data1 = {0.2, 0.3};
		Measurement measurement1 = new Measurement(tester, 
				                                  "measurement1", 
				                                  new WorkList(tester, "worklist", sampleContainer), 
				                                  instrument,
				                                  resultType);
		measurement1.getResults().add( new Result(tester, "result", data1 , 0) );
		
		double[] data2 = {0.4, 0.5};		
		Measurement measurement2 = new Measurement(tester, 
				                                  "measurement", 
				                                  new WorkList(tester, "worklist", sampleContainer), 
				                                  instrument,
				                                  resultType);
		measurement2.getResults().add( new Result(tester, "result", data2 , 0) );
		
		getDAO().save(measurement1);
		getDAO().save(measurement2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(measurement1));
		assertTrue(list.contains(measurement2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.MeasurementDAO#save(net.bioclipse.brunn.pojos.Measurement)}.
	 */
	@Test
	public void testSave() {
		
		double[] data = {0.2, 0.3};
		Measurement measurement = new Measurement(tester, 
				                                  "measurement", 
				                                  new WorkList(tester, "worklist", sampleContainer), 
				                                  instrument,
				                                  resultType);
		measurement.getResults().add( new Result(tester, "result", data , 0) );
		
		getDAO().save(measurement);
		
		session.flush();
		session.clear();
		
		Measurement savedMeasurement = getDAO().getById(measurement.getId());
		assertEquals(measurement, savedMeasurement);
		assertNotSame(measurement, savedMeasurement);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.MeasurementDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		double[] data = {0.2, 0.3};
		Measurement measurement = new Measurement(tester, 
				                                  "measurement", 
				                                  new WorkList(tester, "worklist", sampleContainer), 
				                                  instrument,
				                                  resultType);
		measurement.getResults().add( new Result(tester, "result", data , 0) );
		
		getDAO().save(measurement);
		
		session.flush();
		session.clear();
		
		Measurement savedMeasurement = getDAO().getById(new Long(measurement.getId()));
		assertEquals(measurement, savedMeasurement);
		assertNotSame(measurement, savedMeasurement);		
	}
}
