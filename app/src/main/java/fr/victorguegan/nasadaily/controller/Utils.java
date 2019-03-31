package fr.victorguegan.nasadaily.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getYoutubeIDFromURL (String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        String id = "";
        if (matcher.find()) {
            return matcher.group();
        }
        return id;
    }

}
