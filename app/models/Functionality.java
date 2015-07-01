package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="functionalities")
public class Functionality extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "functionalities_id_seq")
	public Integer id;
  
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;
  
	@JsonIgnore
	@ManyToMany(mappedBy="functionalities")
	public List<Device> devices;

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

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	

}