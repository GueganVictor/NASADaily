package fr.victorguegan.nasadaily.controller;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.view.Detail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.victorguegan.nasadaily.controller.RetroFitClient.API_KEY;
import static fr.victorguegan.nasadaily.controller.RetroFitClient.API_URL;

public class DetailController {

    private final Detail detailActivity;

    private ArrayList<NASA_Item> alNASA;

    public DetailController (Detail detailActivity) {
        this.detailActivity = detailActivity;
        this.alNASA = new ArrayList<>();
    }

    public void start() {

        RetroFitClient r = new RetroFitClient();
        Call<NASA_Item> callAsync = r.getService(API_URL).getNASA_Item(API_KEY, detailActivity.getDate());

        callAsync.enqueue(new Callback<NASA_Item>() {
            @Override
            public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
                detailActivity.showNASA_Item(response.body());
            }

            @Override
            public void onFailure(Call<NASA_Item> call, Throwable throwable) {
                System.out.println("Erreur dans la requête GET :");
                throwable.printStackTrace();
            }
        });

    }

}
