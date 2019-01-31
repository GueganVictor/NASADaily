package fr.victorguegan.nasadaily.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NASA_Service {

    @GET("planetary/apod")
    public Call<NASA_Item> getNASA_Item(@Query("api_key") String api_key);

}
