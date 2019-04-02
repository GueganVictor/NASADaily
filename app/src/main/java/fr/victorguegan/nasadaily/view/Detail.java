package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.polites.android.GestureImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.controller.DetailController;
import fr.victorguegan.nasadaily.controller.Utils;
import fr.victorguegan.nasadaily.model.NASA_Item;

public class Detail extends AppCompatActivity {

    private DetailController controller;

    private String date;

    private Bundle extras;
    TextView textDesc;
    TextView textTitle;
    GestureImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        extras = getIntent().getExtras();
        this.setViews();
        this.controller = new DetailController(this);
        if (extras.getString("mode").equals("date")) {
            this.date = extras.getString("date");
            this.controller.start();
        } else {
            NASA_Item item = extras.getParcelable("item");
            this.showNASA_Item(item);
        }
    }


    public void setViews() {
        this.textDesc  = findViewById(R.id.desc);
        this.textTitle = findViewById(R.id.title);
        this.imageView = findViewById(R.id.image);
    }

    public void showNASA_ItemFromDate (NASA_Item item) {
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

    public void showNASA_Item (NASA_Item item) {

        String title = item.getTitle() + "\n"+ item.getDate();
        textTitle.setText(title);
        textDesc.setText(item.getExplanation());
        String imageTransitionName = extras.getString("transition");
        imageView.setTransitionName(imageTransitionName);

        Picasso.get().load(item.getUrl()).noFade().into(imageView,
            new Callback() {
                @Override
                public void onSuccess() {
                    supportStartPostponedEnterTransition();
                }

                @Override
                public void onError(Exception e) {
                    supportStartPostponedEnterTransition();
                }
            }
        );

    }

    public String getDate() {
        return date;
    }


}
