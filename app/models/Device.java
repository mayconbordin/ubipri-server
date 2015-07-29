package models;

import java.util.*;

import javax.persistence.*;

import models.serialization.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="devices")
public class Device extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devices_id_seq")
    public Integer id;
  
    @Constraints.Required
    @Constraints.MaxLength(50)
    public String code;
    
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String name;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="device_type_id")
    public DeviceType type;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="current_environment_id")
    @JsonView(Views.Full.class)
    public Environment currentEnvironment;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="user_id")
    @JsonIgnore
    public User user;
  
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="device_functionalities",
    joinColumns=
        @JoinColumn(name="device_id", referencedColumnName="id"),
    inverseJoinColumns=
        @JoinColumn(name="functionality_id", referencedColumnName="id")
    )
    @JsonView(Views.Full.class)
    public List<Functionality> functionalities;
  
    @OneToMany
    @JsonView(Views.Full.class)
    public List<DeviceCommunication> communications;

    @JsonIgnore
    public DeviceCommunication getPreferredDeviceCommunication() {
        int preferredIndex = communications.size();
        int preferedOrder = 0;
        
        for (int i=0; i < communications.size();i++) {
        	int order = communications.get(i).getPreferredOrder();
            if (preferredIndex > order) {
                preferredIndex = order;
                preferedOrder = i;
            }
        }
        
        return communications.get(preferedOrder);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	public Environment getCurrentEnvironment() {
		return currentEnvironment;
	}

	public void setCurrentEnvironment(Environment currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Functionality> getFunctionalities() {
		return functionalities;
	}

	public void setFunctionalities(List<Functionality> functionalities) {
		this.functionalities = functionalities;
	}

	public List<DeviceCommunication> getCommunications() {
		return communications;
	}

	public void setCommunications(List<DeviceCommunication> communications) {
		this.communications = communications;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", code=" + code + ", name=" + name
				+ ", type=" + type + ", currentEnvironment="
				+ currentEnvironment + ", user=" + user + ", functionalities="
				+ functionalities + ", communications=" + communications + "]";
	}
}