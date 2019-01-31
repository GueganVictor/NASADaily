package fr.victorguegan.nasadaily;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    String response;

    String API_URL = "https://api.nasa.gov/planetary/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView texte = this.findViewById(R.id.texte);
        ImageView image = this.findViewById(R.id.image);


        texte.setText("avant");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        texte.setText("milmei");
        NASA_Service service = retrofit.create(NASA_Service.class);
        Call<NASA_Item> callAsync = service.getNASA_Item("apod?api_key=NjtGhAKtV5JsG1wyu9Kir7ZD70IQmu95VbPNGzJW");
        texte.setText("2");
        NASA_Call_Back call =  new NASA_Call_Back(image);
        texte.setText("3");
        //((NASA_Call_Back) call).getItem();

        callAsync.enqueue(call);

        

        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(image);


        NASA_Item user = null;




    }
}
