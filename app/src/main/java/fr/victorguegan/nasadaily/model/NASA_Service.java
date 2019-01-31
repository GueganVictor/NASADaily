package fr.victorguegan.nasadaily.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NASA_Service {

    @GET("/{key}")
    public Call<NASA_Item> getNASA_Item(@Path("key") String key);

}
