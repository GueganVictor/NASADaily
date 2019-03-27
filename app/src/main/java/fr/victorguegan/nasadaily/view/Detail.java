package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.polites.android.GestureImageView;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.controller.RetroFitClient;
import fr.victorguegan.nasadaily.model.NASA_Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.victorguegan.nasadaily.view.MainActivity.API_KEY;
import static fr.victorguegan.nasadaily.view.MainActivity.API_URL;

public class Detail extends AppCompatActivity {

    private String date;

    TextView textDesc;
    TextView textTitle;
    GestureImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent myIntent = getIntent();
        date = myIntent.getStringExtra("date");
        textDesc = (TextView)findViewById(R.id.desc);
        textTitle = (TextView)findViewById(R.id.title);
        imageView = findViewById(R.id.image);


        RetroFitClient r = new RetroFitClient();
        Call<NASA_Item> callAsync = r.getService(API_URL).getNASA_Item(API_KEY, date);

        callAsync.enqueue(new Callback<NASA_Item>() {
            @Override
            public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
                NASA_Item myObject = response.body();
                String title = myObject.getTitle() + "\n"+ myObject.getDate();
                textTitle.setText(title);
                textDesc.setText(myObject.getExplanation());

                if (myObject.getMediaType().equals("video")) {

                    String url = myObject.getUrl();
                    String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
                    Pattern compiledPattern = Pattern.compile(pattern);
                    Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
                    String id = "";
                    if (matcher.find()) {
                         id = matcher.group();
                    }
                    url = "https://img.youtube.com/vi/"+id+"/hqdefault.jpg";


                    Picasso.get().load(url).into(imageView);
                } else {
                    Picasso.get().load(myObject.getUrl()).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<NASA_Item> call, Throwable throwable) {
                System.out.println("Erreur dans la requête GET :");
                throwable.printStackTrace();
            }
        });

    }
}
