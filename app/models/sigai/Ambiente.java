package models.sigai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ambiente {
	private int id;
	private String nome;
	
	@JsonProperty("tipo_ambiente_id")
	private int tipoAmbienteId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTipoAmbienteId() {
		return tipoAmbienteId;
	}
	public void setTipoAmbienteId(int tipoAmbienteId) {
		this.tipoAmbienteId = tipoAmbienteId;
	}
}
