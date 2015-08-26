package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="environment_frequency_levels")
public class EnvironmentFrequencyLevel extends Model {
	public static final char METRIC_DAYS   = 'd';
	public static final char METRIC_WEEKS  = 'w';
	public static final char METRIC_MONTHS = 'm';
	public static final char METRIC_YEARS  = 'y';
	
	@EmbeddedId
	private EnvironmentFrequencyLevelPK pk;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="environment_id", insertable = false, updatable = false)
	private Environment environment;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="frequency_level_id", insertable = false, updatable = false)
	private FrequencyLevel frequencyLevel;
	
	private double min;
	private double max;
	private int period;
	private char metric;
	
	public EnvironmentFrequencyLevel() {
		this.pk = new EnvironmentFrequencyLevelPK();
	}

	public EnvironmentFrequencyLevelPK getPk() {
		return pk;
	}

	public void setPk(EnvironmentFrequencyLevelPK pk) {
		this.pk = pk;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		pk.setEnvironmentId(environment.getId());
	}

	public FrequencyLevel getFrequencyLevel() {
		return frequencyLevel;
	}

	public void setFrequencyLevel(FrequencyLevel frequencyLevel) {
		this.frequencyLevel = frequencyLevel;
		pk.setFrequencyLevelId(frequencyLevel.getId());
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public char getMetric() {
		return metric;
	}

	public void setMetric(char metric) {
		this.metric = metric;
	}

	@Override
	public String toString() {
		return "EnvironmentFrequencyLevel{" +
				"min=" + min +
				", max=" + max +
				", period=" + period +
				", metric=" + metric +
				'}';
	}
}
