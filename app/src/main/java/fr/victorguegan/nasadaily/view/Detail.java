package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.polites.android.GestureImageView;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.controller.DetailController;
import fr.victorguegan.nasadaily.controller.Utils;
import fr.victorguegan.nasadaily.model.NASA_Item;

public class Detail extends AppCompatActivity {

    private DetailController controller;

    private String date;

    TextView textDesc;
    TextView textTitle;
    GestureImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent myIntent = getIntent();
        this.date = myIntent.getStringExtra("date");
        this.setViews();
        this.controller = new DetailController(this);
        this.controller.start();
    }


    public void setViews() {
        this.textDesc  = findViewById(R.id.desc);
        this.textTitle = findViewById(R.id.title);
        this.imageView = findViewById(R.id.image);
    }


    public void showNASA_Item (NASA_Item item) {

        String title = item.getTitle() + "\n"+ item.getDate();
        textTitle.setText(title);
        textDesc.setText(item.getExplanation());

        if (item.getMediaType().equals("video")) {
            String url = "https://img.youtube.com/vi/"+ Utils.getYoutubeIDFromURL(item.getUrl()) +"/hqdefault.jpg";
            Picasso.get().load(url).into(imageView);
        } else {
            Picasso.get().load(item.getUrl()).into(imageView);
        }

    }

    public String getDate() {
        return date;
    }


}
