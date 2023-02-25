package br.com.linksport.network;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocalizacaoService {
    @GET("localidades/estados?orderBy=nome")
    Call<List<Estado>> getEstados();

    @GET("localidades/estados/{uf}/municipios")
    Call<List<Cidade>> getCidades(@Path("uf") int uf);
}
