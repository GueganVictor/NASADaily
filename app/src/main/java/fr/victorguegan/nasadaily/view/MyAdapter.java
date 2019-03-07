package fr.victorguegan.nasadaily.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.victorguegan.nasadaily.R;
import fr.victorguegan.nasadaily.model.NASA_Item;
import retrofit2.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final MainActivity act;
    List<NASA_Item> list;

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapter(List<NASA_Item> list, MainActivity act) {
        this.act = act;
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque NASA_Items
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
        private ImageView imageView;
        private Button btn;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            btn = itemView.findViewById(R.id.button);

        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un NASA_Item
        public void bind(NASA_Item myObject){
            String title = myObject.getTitle() + "\n"+ myObject.getDate();
            btn.setTag(myObject.getDate());
            btn.setOnClickListener(act);
            textViewView.setText(title);
            if (myObject.getMediaType().equals("video")) {
                String video_id = myObject.getUrl();
                String s = video_id.split("/")[4];
                String url = "https://img.youtube.com/vi/"+s.substring(0,s.length()-6)+"/hqdefault.jpg";
                Log.d("TESTAT", myObject.getTitle()+" - "+url);
                Picasso.get().load(url).into(imageView);
            } else {
                Picasso.get().load(myObject.getUrl()).into(imageView);
            }

        }
    }

}