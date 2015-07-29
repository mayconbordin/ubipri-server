package models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="user_in_environment")
public class UserEnvironment extends Model {

	@Column(insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_in_environment_id_seq")
	public Integer id;
  
	@EmbeddedId
	public UserEnvironmentPK pk;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="environment_id", insertable = false, updatable = false)
	private Environment environment;
	  
	@ManyToOne(optional=false)
	@JoinColumn(name="user_profile_environment_id")
	public UserProfileEnvironment userProfile;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="current_access_type_id")
	public AccessType currentAccessType;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private User user;
  
	@Column(name = "impact_factor")
	public double impactFactor;
	
	public UserEnvironment() {
		this.pk = new UserEnvironmentPK();
	}
  
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEnvironmentPK getPk() {
		return pk;
	}

	public void setPk(UserEnvironmentPK pk) {
		this.pk = pk;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		this.pk.environmentId = environment.getId();
	}

	public UserProfileEnvironment getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfileEnvironment userProfile) {
		this.userProfile = userProfile;
	}

	public AccessType getCurrentAccessType() {
		return currentAccessType;
	}

	public void setCurrentAccessType(AccessType currentAccessType) {
		this.currentAccessType = currentAccessType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.pk.userId = user.getId();
	}

	public double getImpactFactor() {
		return impactFactor;
	}

	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}
}