package fr.victorguegan.view;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import fr.victorguegan.R;
import fr.victorguegan.controller.Utils;
import fr.victorguegan.model.ItemClickListener;
import fr.victorguegan.model.NASA_Item;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //private final AdapterView.OnItemClickListener listener;
    private List<NASA_Item> local_Dataset;
    private final MainActivity context;
    private final ItemClickListener listener;

    //gere la vue de chaque donnee
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        private TextView textViewView;
        private TextView month;
        private TextView day;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public MyViewHolder(View v) {
            super(v);
            layout = v;

            textViewView = (TextView) itemView.findViewById(R.id.text);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
            month = (TextView) itemView.findViewById(R.id.month);
            day = (TextView) itemView.findViewById(R.id.day);
            imageView = (ImageView) itemView.findViewById(R.id.image);

            int r = new Random().nextInt(5);
        }
    }
    //Constructeur
    public MyAdapter(List<NASA_Item> myDataset, MainActivity context){
        this.listener = context;
        this.context = context;
        local_Dataset = myDataset;
    }

    //creation des nouvelles vues par le layout manager
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        //creation d'une nouvelle vue
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //remplace le contenu de la vue
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //on prend un l'element de notre base a cette position
        //Remplace la vue par son contenu

        final NASA_Item item = local_Dataset.get(position);
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(item.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        holder.month.setText(new SimpleDateFormat("MMMM", Locale.ENGLISH).format(cal.getTime()));
        holder.day.setText(String.format("%s",cal.get(Calendar.DAY_OF_MONTH)));
        String title = item.getTitle();

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/stellar.otf");
        holder.textViewView.setTypeface(custom_font);
        holder.textViewView.setText(title);
        if (item.getMediaType().equals("video")) {
            String url = "https://img.youtube.com/vi/"+ Utils.getYoutubeIDFromURL(item.getUrl()) +"/hqdefault.jpg";
            Picasso.get().load(url).into(holder.imageView);
        } else {
            Picasso.get().load(item.getUrl()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item, holder.imageView);
            }
        });
    }

    //renvoi la taille du dataset
    @Override
    public int getItemCount() {
        return local_Dataset.size();
    }



}