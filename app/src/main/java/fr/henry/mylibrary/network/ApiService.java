package fr.henry.mylibrary.network;

import fr.henry.mylibrary.network.response.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("volumes")
    Call<Response> getBooks(@Query("q") String title, @Query("inauthor") String author, @Query("key") String key);
}
