package services;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.sigai.DaySchedule;
import play.Logger;

public class MockScheduleService implements IScheduleService {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	protected ObjectMapper mapper = new ObjectMapper();

	@Override
	public DaySchedule getSchedule(String matricula, int year, int month, int day) {
		Map<String, DaySchedule> schedule = getSchedule(matricula, year, month);
		
		for (Map.Entry<String, DaySchedule> e : schedule.entrySet()) {
			DateTime date = new DateTime(e.getValue().getData());
			if (date.getYear() == year && date.getMonthOfYear() == month && date.getDayOfMonth() == day) {
				return e.getValue();
			}
		}
		
		return null;
	}
	
	@Override
	public Map<String, DaySchedule> getSchedule(String matricula, int year, int month) {
		Map<String, DaySchedule> schedule = null;
		
		try {
			InputStream is = getClass().getResourceAsStream("/data/schedule.json");
			String json = IOUtils.toString(is);
			schedule = mapper.readValue(json, new TypeReference<Map<String, DaySchedule>>() { });
		} catch (JsonParseException e) {
			logger.error("Error parsing JSON schedule data", e);
		} catch (JsonMappingException e) {
			logger.error("Error mapping JSON to schedule objects", e);
		} catch (IOException e) {
			logger.error("Input error while maaping schedule data", e);
		}

		return schedule;
	}

}
