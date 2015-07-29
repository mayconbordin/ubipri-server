package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserEnvironmentPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	public Integer userId;
    
	@Column(name = "environment_id")
	public Integer environmentId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(Integer environmentId) {
		this.environmentId = environmentId;
	}
	
	public UserEnvironmentPK() {
		
	}
	
	public UserEnvironmentPK(User user, Environment environment) {
		this.userId = user.getId();
		this.environmentId = environment.getId();
	}
	
	public UserEnvironmentPK(int userId, int environmentId) {
		this.userId = userId;
		this.environmentId = environmentId;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEnvironmentPK other = (UserEnvironmentPK) obj;
        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
                return false;
            }
        if ((this.environmentId == null) ? (other.environmentId != null) : !this.environmentId.equals(other.environmentId)) {
            return false;
        }
        return true;
	}

	@Override
	public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 89 * hash + (this.environmentId != null ? this.environmentId.hashCode() : 0);
        return hash;
	}

	@Override
	public String toString() {
		return "UserEnvironmentPK [userId=" + userId + ", environmentId="
				+ environmentId + "]";
	}
}