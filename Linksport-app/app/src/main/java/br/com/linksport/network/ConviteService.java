package br.com.linksport.network;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConviteService {

    @Headers("Content-Type: application/json")
    @POST("convite/cadastrar")
    Call<Convite> enviarConvite(@Query("idUsuario") int idUsuario, @Query("idCard") int idCard);

    @GET("convite/{id}")
    Call<List<Convite>> listarConvites(@Path("id") int idUsuario);

    @Headers("Content-Type: application/json")
    @PUT("convite/atualizar/{id}")
    Call<Convite> atualizarConvite(@Path("id") int id, @Body JsonObject body);
}
