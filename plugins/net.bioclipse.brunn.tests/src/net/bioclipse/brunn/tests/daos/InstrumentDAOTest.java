package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IInstrumentDAO;
import net.bioclipse.brunn.pojos.Instrument;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>InstrumentDAO</code>
 * 
 * @author jonathan
 *
 */
public class InstrumentDAOTest extends AbstractGenericDAOTest {
	
	public InstrumentDAOTest() {
		super("instrumentDAO");
	}
	
	public IInstrumentDAO getDAO(){
		return (IInstrumentDAO)dao;
	}

	@Test
	public void testDelete() {
		
		Instrument instrument = new Instrument(tester, "instrument");
		assertFalse(instrument.isDeleted());
		getDAO().save(instrument);
		
		session.flush();
		session.clear();
		
		getDAO().delete(instrument);
		
		session.flush();
		session.clear();
		
		Instrument deleted = getDAO().getById(new Long(instrument.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.InstrumentDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		Instrument instrument1 = new Instrument(tester, "instrument1");
		Instrument instrument2 = new Instrument(tester, "instrument2");
		getDAO().save(instrument1);
		getDAO().save(instrument2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(instrument1));
		assertTrue(list.contains(instrument2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.InstrumentDAO#save(net.bioclipse.brunn.pojos.Instrument)}.
	 */
	@Test
	public void testSave() {
		
		Instrument instrument = new Instrument(tester, "instrument");
		getDAO().save(instrument);
		
		session.flush();
		session.clear();
		
		Instrument savedInstrument = getDAO().getById(instrument.getId());
		assertEquals(instrument, savedInstrument);
		assertNotSame(instrument, savedInstrument);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.InstrumentDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		Instrument instrument = new Instrument(tester, "instrument");
		getDAO().save(instrument);
		
		session.flush();
		session.clear();
		
		Instrument savedInstrument = getDAO().getById(new Long(instrument.getId()));
		assertEquals(instrument, savedInstrument);
		assertNotSame(instrument, savedInstrument);		
	}
	
	@Test
	public void testGetByName() {
		
		Instrument instrument = new Instrument(tester, "some unique instrumentName");
		getDAO().save(instrument);
		
		session.flush();
		session.clear();
		
		Instrument savedInstrument = getDAO().findByName(instrument.getName()).get(0);
		assertEquals(instrument, savedInstrument);
		assertNotSame(instrument, savedInstrument);		
	}
}
