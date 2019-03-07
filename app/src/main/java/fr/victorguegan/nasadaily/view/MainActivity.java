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
import java.util.Locale;
import java.util.TimeZone;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.controller.RetroFitClient;
import fr.victorguegan.nasadaily.model.NASA_Call_Back;
import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.model.NASA_Service;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        RetroFitClient r = new RetroFitClient();


        for (int i = 0; i < 10; i++) {
            Call<NASA_Item> callAsync = r.getService(API_URL).getNASA_Item(API_KEY, formatter.format(cal.getTime()));
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

            callAsync.enqueue(new Callback<NASA_Item>() {
                @Override
                public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
                    NASA_Item item = response.body();
                    alNASA.add(item);
                    Collections.sort(alNASA, Collections.<NASA_Item>reverseOrder());
                    recyclerView.setAdapter(new MyAdapter(alNASA, MainActivity.this));
                }

                @Override
                public void onFailure(Call<NASA_Item> call, Throwable throwable) {
                    System.out.println("Erreur dans la requête GET :");
                    throwable.printStackTrace();
                }
            });
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

    }


    @Override
    public void onClick(View v) {

        Intent intentMain = new Intent(MainActivity.this ,
                Detail.class);
        intentMain.putExtra("date",(String) v.getTag());
        MainActivity.this.startActivity(intentMain);

    }


}
