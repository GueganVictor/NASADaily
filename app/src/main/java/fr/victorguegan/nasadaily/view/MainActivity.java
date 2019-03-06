package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.model.NASA_Call_Back;
import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.model.NASA_Service;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String API_URL = "https://api.nasa.gov/";
    static final String API_KEY = "NjtGhAKtV5JsG1wyu9Kir7ZD70IQmu95VbPNGzJW";

    ArrayList<NASA_Item> alNASA;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.alNASA = new ArrayList<>();
        setContentView(R.layout.activity_main);

        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 10; i++) {


            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            NASA_Service service = retrofit.create(NASA_Service.class);
            Call<NASA_Item> callAsync = service.getNASA_Item(API_KEY, format1.format(cal.getTime()));
            NASA_Call_Back call =  new NASA_Call_Back(this);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            //définit l'agencement des cellules, ici de façon verticale, comme une ListView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
            //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

            //puis créer un MyAdapter, lui fournir notre liste de villes.
            //cet adapter servira à remplir notre recyclerview


            callAsync.enqueue(new Callback<NASA_Item>() {
                @Override
                public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
                    NASA_Item item = response.body();
                    Log.d("TEST", item.getTitle() +" - "+ item.getUrl());
                    alNASA.add(item);
                    Collections.sort(alNASA, Collections.<NASA_Item>reverseOrder());
                    recyclerView.setAdapter(new MyAdapter(alNASA));
                }

                @Override
                public void onFailure(Call<NASA_Item> call, Throwable throwable) {
                    System.out.println("Erreur dans la requête GET :");
                    throwable.printStackTrace();
                }
            });
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }



        Log.d("TEST", alNASA.toString());

    }


}
