package br.com.linksport.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiaService {

    @GET("everything?language=pt&q=esporte&pageSize=25&sortBy=publishedAt&apiKey=44b89075a8814575ac820bdf038d0c9d")
    Call<NewsResponse> listarNoticias();

}
