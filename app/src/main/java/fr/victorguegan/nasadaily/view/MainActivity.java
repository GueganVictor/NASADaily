package fr.victorguegan.nasadaily.view;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import fr.victorguegan.nasadaily.controller.MainController;
import fr.victorguegan.nasadaily.controller.RetroFitClient;
import fr.victorguegan.nasadaily.model.ItemClickListener;
import fr.victorguegan.nasadaily.model.NASA_Call_Back;
import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.model.NASA_Service;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements ItemClickListener {

    public MainController controller;

    RecyclerView recyclerView;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setViews();
        this.setFont();
        controller = new MainController(this);
        controller.start();
    }

    public void setViews() {
        textTitle = findViewById(R.id.toolbar_title);
    }

    public void setFont() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/stargaze.ttf");
        textTitle.setTypeface(custom_font);
    }


    public void showList(List<NASA_Item> list) {
        recyclerView = findViewById(R.id.recyclerView);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setAdapter(new MyAdapter(list, this));
    }

    @Override
    public void onItemClick(NASA_Item item, ImageView imageView) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("item", item);
        intent.putExtra("transition", ViewCompat.getTransitionName(imageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, ViewCompat.getTransitionName(imageView));

        startActivity(intent, options.toBundle());

    }
}
