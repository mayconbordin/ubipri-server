package models.sigai;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.json.CustomJsonDateDeserializer;
import utils.json.CustomJsonTimeDeserializer;

public class Aula {
	private int id;
	
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date data;
	
	@JsonDeserialize(using = CustomJsonTimeDeserializer.class)
	@JsonProperty("horario_inicio")
	private Date horarioInicio;
	
	@JsonDeserialize(using = CustomJsonTimeDeserializer.class)
	@JsonProperty("horario_fim")
	private Date horarioFim;
	
	private String status;
	private String conteudo;
	
	@JsonProperty("ensino_a_distancia")
	private boolean ensinoADistancia;
	
	@JsonProperty("turma_id")
	private int turmaId;
	
	private Ambiente ambiente;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getHorarioInicio() {
		return horarioInicio;
	}
	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	public Date getHorarioFim() {
		return horarioFim;
	}
	public void setHorarioFim(Date horarioFim) {
		this.horarioFim = horarioFim;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public boolean isEnsinoADistancia() {
		return ensinoADistancia;
	}
	public void setEnsinoADistancia(boolean ensinoADistancia) {
		this.ensinoADistancia = ensinoADistancia;
	}
	public int getTurmaId() {
		return turmaId;
	}
	public void setTurmaId(int turmaId) {
		this.turmaId = turmaId;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	
	
}
