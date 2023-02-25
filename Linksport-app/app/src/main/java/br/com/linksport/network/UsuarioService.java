package br.com.linksport.network;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UsuarioService {
    @GET("card/{id}")
    Call<List<Card>> getCards(@Path("id") int usuarioId);

    @GET("usuario/{id}")
    Call<Usuario> buscarUsuarioPorId(@Path("id") int id);

    @GET("usuario/buscar/{email}")
    Call<Usuario> buscarEmail(@Path("email") String email);

    @GET("usuario/buscar/user/{usuario}")
    Call<Usuario> buscarUsuario(@Path("usuario") String usuario);

    @Headers("Content-Type: application/json")
    @POST("usuario/login")
    Call<Usuario> login(@Body JsonObject body);
    //Call<Usuario> login(@Field("email") String email, @Field("senha") String senha);

    @Headers("Content-Type: application/json")
    @POST("usuario/cadastrar")
    Call<Usuario> cadastrarUsuario(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @DELETE("usuario/apagar/{id}")
    Call<Usuario> apagarUsuario(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @PUT("usuario/atualizar/{id}")
    Call<Usuario> atualizarUsuario(@Path("id") int id, @Body JsonObject body);
}
