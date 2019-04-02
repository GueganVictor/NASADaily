package fr.victorguegan.nasadaily.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
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

public class MainActivity extends AppCompatActivity  implements ItemClickListener, View.OnClickListener {

    public MainController controller;

    private Calendar myCalendar = Calendar.getInstance();
    RecyclerView recyclerView;
    private TextView textTitle;
    private ImageButton button;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setViews();
        this.setFont();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                openDetail(sdf.format(myCalendar.getTime()));
            }
        };
        controller = new MainController(this);
        controller.start();
    }

    public void setViews() {
        textTitle = findViewById(R.id.toolbar_title);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void setFont() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/stargaze.ttf");
        textTitle.setTypeface(custom_font);
    }

    public void openDetail(String date) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("mode", "date");
        intent.putExtra("date", date);
        startActivity(intent);
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
        intent.putExtra("mode", "transition");
        intent.putExtra("transition", ViewCompat.getTransitionName(imageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, ViewCompat.getTransitionName(imageView));

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY", Locale.US);
        Date d;
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        DatePickerDialog dp = new DatePickerDialog(MainActivity.this, this.date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)-1);
        c.add(Calendar.DAY_OF_MONTH,-1);
        try {
            d = format.parse("16-06-1996");
            c.setTime(d);
            dp.getDatePicker().setMinDate(c.getTimeInMillis());
        } catch (ParseException e) { e.printStackTrace(); }

        dp.show();
    }

    @Override
    public void onBackPressed() {
        //do noting
    }

}
