package br.com.linksport.model.dto;

public class UsuarioDTO {
	
	private long id;
    private String nomeCompleto;
    private String usuario;
    private String email;
    private String dataNascimento;
    private String uf;
    
	public UsuarioDTO(long id, String nomeCompleto, String usuario, String email, String dataNascimento, String uf) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.usuario = usuario;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.uf = uf;
	}

	public UsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
    
}
