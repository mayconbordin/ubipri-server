package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="log_events")
public class LogEvent extends Model {
	/**
	 * Day shift
	 */
	public static final char DAY_SHIFT   = 'd';
	
	/**
	 * Night shift
	 */
	public static final char NIGHT_SHIFT = 'n';

	/**
	 * Weekend
	 */
	public static final int DAY_OF_WEEKEND = 0;
	
	/**
	 * Weekday
	 */
	public static final int DAY_OF_WEEK    = 1;

	/**
	 * Is a workday
	 */
	public static final char YES_WORKDAY = 'y';
	
	/**
	 * Is not a workday
	 */
	public static final char NOT_WORKDAY = 'n';
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_events_id_seq")
	public Integer id;
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="log_time")
	public Date time;
	
	public Character shift;
	public Integer weekday;
	public Character workday;
	
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String event;
	
	@Constraints.MaxLength(100)
	public String code;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="environment_id")
	public Environment environment;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="user_id")
	public User user;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="device_id")
	public Device device;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Character getShift() {
		return shift;
	}

	public void setShift(Character shift) {
		this.shift = shift;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public Character getWorkday() {
		return workday;
	}

	public void setWorkday(Character workday) {
		this.workday = workday;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}



}