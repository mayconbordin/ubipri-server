package unit.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import models.Environment;
import models.EnvironmentType;
import models.LocalizationType;

import org.junit.Before;
import org.junit.Test;
import org.postgis.Point;

import play.Logger;
import utils.gis.GeometryBuilder;
import base.ApplicationBaseTest;
import dao.ebean.EnvironmentEbeanDAO;

public class EnvironmentDAOTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private EnvironmentEbeanDAO dao;

	@Before
	public void setUp() {
		dao = new EnvironmentEbeanDAO();
		dao.setConnection(database.getConnection());
	}

	@Test
    public void testInsert() throws Exception {
		logger.debug("testInsert");
		
		EnvironmentType type = new EnvironmentType();
		type.setId(1);

		LocalizationType locType = new LocalizationType();
		locType.setId(1);
		
		Environment e = new Environment();
		e.setName("Home");
		e.setLocation(GeometryBuilder.createPoint3d(-30.039857387542725,-51.20896339416505, 0.0));
		e.setOperatingRange(1.0);
		e.setVersion(1);
		e.setEnvironmentType(type);
		e.setLocalizationType(locType);
		
		e = dao.create(e);
		
		assertNotNull(e);
		assertNotNull(e.getId());
		assertTrue(e.getId() > 0);
		
		assertNotNull(dao.find(e.getId()));
	}
	
	@Test
    public void testInsertWithShape() throws Exception {
		logger.debug("testInsertWithShape");
		
		EnvironmentType type = new EnvironmentType();
		type.setId(1);
		
		LocalizationType locType = new LocalizationType();
		locType.setId(1);

		Environment e = new Environment();
		e.setName("Home");
		e.setLocation(GeometryBuilder.createPoint3d(-30.039857387542725,-51.20896339416505, 0.0));
		e.setShape(GeometryBuilder.createPolygon(new Point[] {
				new Point(-29.9612808227539,-51.198184967041,0.0),
				new Point(-30.1073989868164,-51.2952117919922,0.0),
				new Point(-30.2264022827148,-51.216136932373,0.0),
				new Point(-30.0949935913086,-51.0650444030762,0.0),
				new Point(-29.9714050292969,-51.1136016845703,0.0),
				new Point(-29.9612808227539,-51.198184967041,0.0)
		}));
		e.setOperatingRange(1.0);
		e.setVersion(1);
		e.setEnvironmentType(type);
		e.setLocalizationType(locType);
		
		e = dao.create(e);
		
		assertNotNull(e);
		assertNotNull(e.getId());
		assertTrue(e.getId() > 0);
		
		assertNotNull(dao.find(e.getId()));
	}
	
	@Test
	public void testFind() throws Exception {
		logger.debug("testFind");
		
		EnvironmentType type = new EnvironmentType();
		type.setId(1);

		LocalizationType locType = new LocalizationType();
		locType.setId(1);
		
		Environment e = new Environment();
		e.setName("Home");
		e.setLocation(GeometryBuilder.createPoint3d(-30.039857387542725,-51.20896339416505, 0.0));
		e.setOperatingRange(1.0);
		e.setVersion(1);
		e.setEnvironmentType(type);
		e.setLocalizationType(locType);
		
		e = dao.create(e);
		
		Environment test = dao.find(e.getId());
		
		assertEquals("Home", test.getName());
		assertEquals("Restrict", test.getEnvironmentType().getName());
		assertEquals("GPS", test.getLocalizationType().getName());
		
		assertNotNull(test.getLocation());
		assertEquals(-30.039857387542725, test.getLocation().getX(), 0);
		assertEquals(-51.20896339416505, test.getLocation().getY(), 0);
	}

	@Test
	public void testFindExisting() throws Exception {
		logger.debug("testFindExisting");

		Environment env = dao.findWith(2, "parent");

		assertEquals("Campus Vale UFRGS", env.getName());
		assertEquals(1, env.getVersion());

		logger.debug("Parent: "+env.getParent());
	}
	
	@Test
    public void testUpdate() throws Exception {
		logger.debug("testUpdate");
		
		Environment e = dao.find(1);
		
		e.setName("POA");
		e.setLocation(GeometryBuilder.createPoint3d(-30.039857387542725,-51.20896339416505, 0.0));

		e = dao.update(e);
		
		assertNotNull(e);
		assertNotNull(e.getId());
	}
	
	@Test
	public void testFindNearBy() {
		logger.debug("testFindNearBy");
		
		Point p = GeometryBuilder.createPoint3d(-30.0719275474548, -51.1200799942017, 0.0);
		List<Environment> list = dao.findNearBy(p, 400);

		for (Environment e : list) {
			logger.debug("Environment "+e.getName()+", distance: "+e.getDistance());
		}
		
		assertEquals(3, list.size());
		
	}
	
	@Test
	public void testFindAll() {
		logger.debug("testFindAll");
		
		List<Environment> list = dao.findAll();
		assertTrue(list.size() >= 3);
	}
	
	@Test
	public void testFindAllWithLimit() {
		logger.debug("testFindAllWithLimit");
		
		List<Environment> list = dao.findAll(2);
		assertEquals(2, list.size());
	}
}
