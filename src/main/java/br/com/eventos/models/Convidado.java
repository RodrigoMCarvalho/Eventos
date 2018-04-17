package br.com.eventos.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Convidado {
	
	@Id
	@NotEmpty
	private String rg;
	@NotEmpty
	private String nome;
	
	@ManyToOne(cascade = CascadeType.REMOVE) //muitos convidados para um evento
	@JoinColumn(name="evento")
	private Evento evento;
	
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	
}
