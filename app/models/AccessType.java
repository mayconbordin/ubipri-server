package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="access_types")
public class AccessType extends Model {
	public static final AccessType BLOCKED  = new AccessType(1, "Blocked");
	public static final AccessType GUEST    = new AccessType(2, "Guest");
	public static final AccessType BASIC    = new AccessType(3, "Basic");
	public static final AccessType ADVANCED = new AccessType(4, "Advanced");
	public static final AccessType ADMIN    = new AccessType(5, "Administrative");

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_types_id_seq")
	public Integer id;
  
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;

	public AccessType() { }

	public AccessType(int id, String name) {
		this.id   = id;
		this.name = name;
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
		return "AccessType [id=" + id + ", name=" + name + "]";
	}
}