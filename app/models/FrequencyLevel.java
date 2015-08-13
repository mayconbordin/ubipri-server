package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints;

@Entity
@Table(name="frequency_levels")
public class FrequencyLevel {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frequency_levels_id_seq")
    public Integer id;
	
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;
	
	@Constraints.Required
	public Integer level;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}
