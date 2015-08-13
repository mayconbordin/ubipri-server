package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="access_type_classifier")
public class AccessTypeClassifier extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_type_classifier_id_seq")
	public Integer id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_profile_environment_id")
	public UserProfileEnvironment userProfile;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="environment_type_id")
    public EnvironmentType environmentType;

	@Constraints.Required
	public Character shift;
	
	@Constraints.Required
	public Integer weekday;
	
	@Constraints.Required
	public Character workday;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="access_type_id")
	public AccessType accessType;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="frequency_level_id")
	public FrequencyLevel frequencyLevel;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public UserProfileEnvironment getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfileEnvironment userProfile) {
		this.userProfile = userProfile;
	}

	public EnvironmentType getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
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

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public FrequencyLevel getFrequencyLevel() {
		return frequencyLevel;
	}

	public void setFrequencyLevel(FrequencyLevel frequencyLevel) {
		this.frequencyLevel = frequencyLevel;
	}

	@Override
	public String toString() {
		return "AccessTypeClassifier [id=" + id + ", userProfile="
				+ userProfile + ", environmentType=" + environmentType
				+ ", shift=" + shift + ", weekday=" + weekday + ", workday="
				+ workday + ", accessType=" + accessType + "]";
	}
}