package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.model.NASA_Item;
import retrofit2.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final MainActivity act;
    List<NASA_Item> list;


    public MyAdapter(List<NASA_Item> list, MainActivity act) {
        this.act = act;
        this.list = list;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_list,viewGroup,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        NASA_Item myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewView;
        private TextView month;
        private TextView day;
        private ImageView imageView;
        private Button btn;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewView = (TextView) itemView.findViewById(R.id.text);
            month = (TextView) itemView.findViewById(R.id.month);
            day = (TextView) itemView.findViewById(R.id.day);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            btn = itemView.findViewById(R.id.button);

        }


        public void bind(NASA_Item myObject){
            Calendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                cal.setTime(sdf.parse(myObject.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            month.setText(new SimpleDateFormat("MMMM", Locale.ENGLISH).format(cal.getTime()));
            day.setText(""+cal.get(Calendar.DAY_OF_MONTH));
            String title = myObject.getTitle();
            btn.setTag(myObject.getDate());
            btn.setOnClickListener(act);
            Typeface custom_font = Typeface.createFromAsset(act.getAssets(),  "fonts/stellar.otf");
            btn.setTypeface(custom_font);
            btn.setText(btn.getText().toString().toUpperCase());
            textViewView.setTypeface(custom_font);
            textViewView.setText(title);

            if (myObject.getMediaType().equals("video")) {
                String video_id = myObject.getUrl();
                String s = video_id.split("/")[4];
                String url = "https://img.youtube.com/vi/"+s.substring(0,s.length()-6)+"/hqdefault.jpg";
                Picasso.get().load(url).into(imageView);
            } else {
                Picasso.get().load(myObject.getUrl()).into(imageView);
            }
        }
    }

}