package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserEnvironmentPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	public Long userId;
    
	@Column(name = "environment_id")
	public Long environmentId;
    
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
}