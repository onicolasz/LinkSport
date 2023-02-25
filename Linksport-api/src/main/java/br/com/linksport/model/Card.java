package br.com.linksport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Card {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column(nullable = false, length=512)
    private String titulo;
	@Column(nullable = false, length=15)
    private String data;
	@Column(nullable = false, length=100)
    private String status;
	@Column(nullable = false, length=512)
    private String evento;
	@Column(nullable = false, length=15)
    private String hora;
	@Column(nullable = false, length=512)
    private String localizacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;
    
	public Card() {
		super();
	}

	public Card(String titulo, String data, String status, String evento, String hora,
			String localizacao, Usuario usuario) {
		super();
		this.titulo = titulo;
		this.data = data;
		this.status = status;
		this.evento = evento;
		this.hora = hora;
		this.localizacao = localizacao;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
    
}


