package fr.victorguegan.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import fr.victorguegan.R;
import fr.victorguegan.controller.MainController;
import fr.victorguegan.model.ItemClickListener;
import fr.victorguegan.model.NASA_Item;

public class MainActivity extends AppCompatActivity implements ItemClickListener, View.OnClickListener {

    public MainController controller;

    private MyAdapter adapter;

    private Calendar myCalendar = Calendar.getInstance();
    RecyclerView recyclerView;
    private TextView textTitle;
    private ImageButton button;
    private SearchView searchView;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setViews();
        this.setFont();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return true;
            }
        });

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
        findViewById(R.id.fragment_container).setVisibility(View.GONE);
        textTitle = findViewById(R.id.toolbar_title);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        searchView = findViewById(R.id.search);
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
        adapter = new MyAdapter(list, this);
        recyclerView.setAdapter(adapter);
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
        dp.getDatePicker().setMaxDate(c.getTimeInMillis());
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
