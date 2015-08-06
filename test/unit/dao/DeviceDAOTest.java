package unit.dao;

import models.Device;

import org.junit.Test;
import static org.junit.Assert.*;

import play.Logger;
import base.ApplicationBaseTest;
import dao.ebean.DeviceEbeanDAO;

public class DeviceDAOTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	private DeviceEbeanDAO deviceDao;

	public DeviceDAOTest() {
		deviceDao = new DeviceEbeanDAO();
	}

	@Test
    public void testFindByFieldCode() throws Exception {
		logger.debug("testFindByFieldCode");
		
		Device device = deviceDao.findByField("code", "5E-EF-8-9B-54");
		assertNotNull(device);
		assertEquals("RFID Card - Borges", device.getName());
	}
}
