package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="environment_types")
public class EnvironmentType extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environment_types_id_seq")
	public Integer id;
  
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;

	public EnvironmentType() {}
	public EnvironmentType(int id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "EnvironmentType [id=" + id + ", name=" + name + "]";
	}
}