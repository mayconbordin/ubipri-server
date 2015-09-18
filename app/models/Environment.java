package models;

import java.util.*;

import javax.persistence.*;

import org.postgis.Point;
import org.postgis.Polygon;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="environments")
public class Environment extends Model {
	public static final int SRID = 4326;
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environments_id_seq")
    public Integer id;
    
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String name;

    public Point location;

    public Polygon shape;
  
    @Column(name="operating_range", nullable=false)
    public double operatingRange;

    public int version;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="localization_type_id")
    public LocalizationType localizationType;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="environment_type_id")
    public EnvironmentType environmentType;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="parent_environment_id")
	@JsonIgnore
    public Environment parent;

	@Transient
	public Integer parentId;

	public Integer level;

    @OneToMany
	@JsonIgnore
    public List<Action> customActions;
    
    /**
     * Used only for results with distance from a point
     */
    @Transient
    public double distance;

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

	public double getOperatingRange() {
		return operatingRange;
	}

	public void setOperatingRange(double operatingRange) {
		this.operatingRange = operatingRange;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalizationType getLocalizationType() {
		return localizationType;
	}

	public void setLocalizationType(LocalizationType localizationType) {
		this.localizationType = localizationType;
	}

	public EnvironmentType getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
	}

	public Environment getParent() {
		return parent;
	}

	public void setParent(Environment parent) {
		this.parent = parent;
		this.parentId = parent.getId();
	}

	public List<Action> getCustomActions() {
		return customActions;
	}

	public void setCustomActions(List<Action> customActions) {
		this.customActions = customActions;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
		this.location.setSrid(SRID);
	}

	public Polygon getShape() {
		return shape;
	}

	public void setShape(Polygon shape) {
		this.shape = shape;
		this.shape.setSrid(SRID);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Environment{" +
				"id=" + id +
				", name='" + name + '\'' +
				", location=" + location +
				", shape=" + shape +
				", operatingRange=" + operatingRange +
				", version=" + version +
				", localizationType=" + localizationType +
				", environmentType=" + environmentType +
				", parent=" + parent +
				", parentId=" + parentId +
				", level=" + level +
				", customActions=" + customActions +
				", distance=" + distance +
				'}';
	}
}
