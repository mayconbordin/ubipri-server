package models.sigai;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.json.CustomJsonDateDeserializer;

public class DaySchedule {
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date data;
	
	private List<Aula> aulas;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<Aula> getAulas() {
		return aulas;
	}
	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
}
