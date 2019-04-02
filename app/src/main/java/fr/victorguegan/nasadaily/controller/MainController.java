package fr.victorguegan.nasadaily.controller;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.view.Detail;
import fr.victorguegan.nasadaily.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private final MainActivity mainActivity;

    private ArrayList<NASA_Item> alNASA;

    public MainController (MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.alNASA = new ArrayList<>();
    }

    public void start() {

        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        RetroFitClient r = new RetroFitClient();
        Call<NASA_Item> callAsync;
        for (int i = 0; i < 10; i++) {
            callAsync = r.getService(RetroFitClient.API_URL).getNASA_Item(RetroFitClient.API_KEY, formatter.format(cal.getTime()));

            callAsync.enqueue(new Callback<NASA_Item>() {
                @Override
                public void onResponse(Call<NASA_Item> call, Response<NASA_Item> response) {
                    alNASA.add(response.body());
                    Collections.sort(alNASA, Collections.<NASA_Item>reverseOrder());
                    mainActivity.showList(alNASA);
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


}
