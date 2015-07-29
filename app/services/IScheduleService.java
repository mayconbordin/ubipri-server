package services;

import java.util.Map;

import models.sigai.DaySchedule;

public interface IScheduleService {
	/**
	 * Get the schedule of an user for the given year and month.
	 * 
	 * @param matricula
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, DaySchedule> getSchedule(String matricula, int year, int month);
	
	/**
	 * Get the schedule for an user for the given day.
	 * 
	 * @param matricula
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public DaySchedule getSchedule(String matricula, int year, int month, int day);
}
