package fr.victorguegan.nasadaily.model;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.victorguegan.nasadaily.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NASA_Call_Back implements Callback<NASA_Item> {

    private AppCompatActivity app;
    private NASA_Item item;

    public NASA_Call_Back( AppCompatActivity app) {
        this.app = app;
    }

    public NASA_Item getItem () {
        return this.item;
    }

    @Override
    public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
        item = response.body();
        if (item != null) {
            Picasso.get().load(item.getUrl()).into((ImageView)app.findViewById(R.id.image));
            TextView texte = app.findViewById(R.id.title);
            texte.setText(item.getTitle());
        }
    }

    @Override
    public void onFailure(Call<NASA_Item> call, Throwable throwable) {
        System.out.println("Erreur dans la requête GET :");
        throwable.printStackTrace();
    }

}
