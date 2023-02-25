package br.com.linksport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Convite {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = false, length=10)
	private String resposta;
	@ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	@ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    
	public Convite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Convite(Usuario usuario, Card card, String resposta) {
		super();
		this.usuario = usuario;
		this.card = card;
		this.resposta = resposta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
}
