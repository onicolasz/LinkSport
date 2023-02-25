package br.com.linksport.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("evento")
    @Expose
    private String evento;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("localizacao")
    @Expose
    private String localizacao;
    private Usuario usuario;

    public Integer getId() {

        return id;
    }

    public String getTitulo() {

        return titulo;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {

        return status;
    }

    public String getEvento() {

        return evento;
    }

    public String getHora() {

        return hora;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
