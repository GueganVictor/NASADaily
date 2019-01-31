package fr.victorguegan.nasadaily.model;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NASA_Call_Back implements Callback<NASA_Item> {

    public boolean gotAnswer = false;

    private final ImageView image;
    private NASA_Item item;

    public NASA_Call_Back(ImageView image) {
        this.image = image;
    }

    public NASA_Item getItem () {
        return this.item;
    }

    @Override
    public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
        NASA_Item item = response.body();
        gotAnswer = true;

    }

    @Override
    public void onFailure(Call<NASA_Item> call, Throwable throwable) {
        System.out.println(throwable);
    }

}
