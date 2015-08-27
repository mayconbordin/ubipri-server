package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="user_profile_environments")
public class UserProfileEnvironment extends Model {
	/*  Automatically classified IDs of the Default User Profiles in Environment */
	public static final int UNKNOW    = 1;
	public static final int TRANSIENT = 2;
	public static final int USER 	  = 3;
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_environments_id_seq")
	public Integer id;
  
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;

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
}