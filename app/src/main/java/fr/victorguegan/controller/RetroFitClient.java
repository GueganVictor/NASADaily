package fr.victorguegan.controller;

import fr.victorguegan.model.NASA_Service;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    public static final String API_URL = "https://api.nasa.gov/";
    public static final String API_KEY = "NjtGhAKtV5JsG1wyu9Kir7ZD70IQmu95VbPNGzJW";

    public NASA_Service getService (String url) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(NASA_Service.class);
    }

}
