package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EnvironmentFrequencyLevelPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "environment_id")
	private Integer environmentId;
	
	@Column(name = "frequency_level_id")
	private Integer frequencyLevelId;
	
	public EnvironmentFrequencyLevelPK() {
		
	}
	
	public EnvironmentFrequencyLevelPK(Environment environment, FrequencyLevel frequencyLevel) {
		this.environmentId    = environment.getId();
		this.frequencyLevelId = frequencyLevel.getId();
	}
	
	public EnvironmentFrequencyLevelPK(Integer environmentId, Integer frequencyLevelId) {
		this.environmentId = environmentId;
		this.frequencyLevelId = frequencyLevelId;
	}

	public Integer getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(Integer environmentId) {
		this.environmentId = environmentId;
	}

	public Integer getFrequencyLevelId() {
		return frequencyLevelId;
	}

	public void setFrequencyLevelId(Integer frequencyLevelId) {
		this.frequencyLevelId = frequencyLevelId;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnvironmentFrequencyLevelPK other = (EnvironmentFrequencyLevelPK) obj;
        if ((this.frequencyLevelId == null) ? (other.frequencyLevelId != null) : !this.frequencyLevelId.equals(other.frequencyLevelId)) {
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
        hash = 89 * hash + (this.frequencyLevelId != null ? this.frequencyLevelId.hashCode() : 0);
        hash = 89 * hash + (this.environmentId != null ? this.environmentId.hashCode() : 0);
        return hash;
	}
}
