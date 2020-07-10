package view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikinav.R;

import java.util.ArrayList;



public class LinksHollder extends RecyclerView.Adapter<LinksHollder.ViewHolder> {



    //Pointeur vers une classe qui implémente OnFieldsListener
    private OnlinkListener onlinkListener;
    private ArrayList<String> allLinks;

    public LinksHollder(ArrayList<String> allLinks, OnlinkListener onlinkListener) {
        this.allLinks = allLinks;
        this.onlinkListener = onlinkListener;
    }


    /**
     * Méthode qui permet de créer une ligne mais que nous n'appellerons jamais nous-mêmes
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_line, parent, false);

        return new LinksHollder.ViewHolder(view);
    }

    /**
     * Méthode qui remplit une ligne créée mais que nous n'appellerons jamais nous-mêmes
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String  str= allLinks.get(position);

        holder.tv_links.setText(str);



        holder.root_link_line .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onlinkListener != null) {
                    onlinkListener.onClick(str);
                }
            }
        });
    }

    /**
     * Méthode qui indique le nombre de lignes à créer mais que nous n'appellerons jamais nous-mêmes
     */
    @Override
    public int getItemCount() {
        return allLinks.size();
    }

    /**
     * Classe interne représentant les pointeurs vers les composants graphiques d'une ligne de la liste
     * Il y aura une instance de cette classe par ligne
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_links;
        public View root_link_line ;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_links = (TextView) itemView.findViewById(R.id.tv_links);
            root_link_line  = itemView.findViewById(R.id.root_link_line);
        }
    }

    //Notre moyen de communication
    public interface OnlinkListener {
        void onClick(String article);
    }


}



