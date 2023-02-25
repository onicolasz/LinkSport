package br.com.linksport.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Convite {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("resposta")
    @Expose
    private String resposta;
    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
    @SerializedName("card")
    @Expose
    private Card card;

    public Integer getId() {
        return id;
    }

    public String getResposta() {
        return resposta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Card getCard() {
        return card;
    }
}
