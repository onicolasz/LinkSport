package br.com.linksport.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static UsuarioService INSTANCE;
    private static CardService INSTANCE2;
    private static LocalizacaoService INSTANCE3;
    private static ConviteService INSTANCE4;
    private static NoticiaService noticiaService;

    public static UsuarioService getInstance() {
        if (INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(UsuarioService.class);
        }
        return INSTANCE;
    }

    public static CardService getINSTANCE2() {
        if (INSTANCE2 == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE2 = retrofit.create(CardService.class);
        }
        return INSTANCE2;
    }

    public static LocalizacaoService getINSTANCE3() {
        if (INSTANCE3 == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://servicodados.ibge.gov.br/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE3 = retrofit.create(LocalizacaoService.class);
        }
        return INSTANCE3;
    }

    public static ConviteService getInstance4() {
        if (INSTANCE4 == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE4 = retrofit.create(ConviteService.class);
        }
        return INSTANCE4;
    }

    public static NoticiaService getNoticiaService() {
        if (noticiaService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            noticiaService = retrofit.create(NoticiaService.class);
        }
        return noticiaService;
    }
}
