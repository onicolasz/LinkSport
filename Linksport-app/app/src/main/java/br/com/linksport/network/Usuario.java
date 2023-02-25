package br.com.linksport.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Usuario {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nomeCompleto")
    @Expose
    private String nomeCompleto;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("senha")
    @Expose
    private String senha;
    @SerializedName("dataNascimento")
    @Expose
    private String dataNascimento;
    @SerializedName("uf")
    @Expose
    private String uf;
    @SerializedName("fotoPerfil")
    @Expose
    private String fotoPerfil;

    public int getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getUf() {
        return uf;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }


}
