package fr.victorguegan.nasadaily.controller;

import java.util.ArrayList;

import fr.victorguegan.nasadaily.model.NASA_Item;
import fr.victorguegan.nasadaily.view.Detail;

public class DetailController {

    private final Detail detailActivity;

    private ArrayList<NASA_Item> alNASA;

    public DetailController (Detail detailActivity) {
        this.detailActivity = detailActivity;
        this.alNASA = new ArrayList<>();
    }

    public void start() {



    }

}
