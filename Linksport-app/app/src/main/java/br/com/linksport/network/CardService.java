package br.com.linksport.network;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CardService {
    @Headers("Content-Type: application/json")
    @POST("card/{id}")
    Call<Card> addCard(@Path("id") int usuarioId, @Body JsonObject body);

    @Headers("Content-Type: application/json")
    @PUT("card/{id}")
    Call<Card> atualizarCard(@Path("id") int cardid, @Body JsonObject body);

    @Headers("Content-Type: application/json")
    @DELETE("card/{id}")
    Call<Card> apagarCard(@Path("id") int cardid);
}
