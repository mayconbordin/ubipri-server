package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints;

@Entity
@Table(name="user_credentials")
public class UserCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_credentials_id_seq")
	public Integer id;
	
	@Constraints.Required
	@Constraints.MaxLength(255)
	@Column(name="external_id")
	public String externalId;
	
	@Constraints.Required
	@Constraints.MaxLength(50)
	public String system;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	public User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
