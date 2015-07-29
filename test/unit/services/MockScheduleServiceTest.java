package unit.services;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import models.sigai.DaySchedule;
import services.MockScheduleService;

public class MockScheduleServiceTest {
	protected MockScheduleService service;
	
	@Before
	public void setUp() {
		service = new MockScheduleService();
	}

    @Test
    public void testGetSchedule() {
    	System.out.println("testGetSchedule");
    	
    	Map<String, DaySchedule> schedule = service.getSchedule("0000", 2014, 8);
    	
    	for (Map.Entry<String, DaySchedule> e : schedule.entrySet()) {
    		//System.out.println(e.getKey() + ": " + e.getValue().getAulas().size());
    	}
    	
    	assertEquals(31, schedule.size());
    	
    	// Check if these dates have one aula
    	for (String k : new String[] {"1", "5", "8", "12", "15", "19", "22", "26", "29"}) {
    		assertEquals(1, schedule.get(k).getAulas().size());
    	}
    }
    
    @Test
    public void testGetScheduleSingleDay() {
    	System.out.println("testGetScheduleSingleDay");
    	
    	DaySchedule schedule = service.getSchedule("0000", 2014, 8, 1);
    	
    	assertEquals(1, schedule.getAulas().size());
    }

}