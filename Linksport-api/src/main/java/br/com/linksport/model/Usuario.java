package br.com.linksport.model;

import br.com.linksport.model.dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column(nullable = false, length=200)
    private String nomeCompleto;
	@Column(nullable = false, length=50)
    private String usuario;
	@Column(nullable = false, length=100)
    private String email;
	@Column(nullable = false, length=512)
    private String senha;
	@Column(nullable = false, length=15)
    private String dataNascimento;
	@Column(nullable = false, length=100)
    private String uf;
    
	public Usuario() {
		super();
	}
	
	public Usuario(String nomeCompleto,String usuario, String email, String senha, String dataNascimento, String uf) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.usuario = usuario;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.uf = uf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public UsuarioDTO obterUsuarioDTO() {
        return new UsuarioDTO(this.id, this.nomeCompleto, this.usuario, this.email, this.dataNascimento, this.uf);
    }
}
