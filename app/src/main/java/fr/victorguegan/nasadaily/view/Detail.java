package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent myIntent = getIntent();
        date = myIntent.getStringExtra("date");
        textDesc = (TextView)findViewById(R.id.desc);
        textTitle = (TextView)findViewById(R.id.title);
        imageView = (ImageView)findViewById(R.id.image);


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
                    String video_id = myObject.getUrl();
                    String s = video_id.split("/")[4];
                    String url = "https://img.youtube.com/vi/"+s.substring(0,s.length()-6)+"/hqdefault.jpg";
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
