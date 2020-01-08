package com.takuchan.kyudo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class PlayerafterRealmAdapter extends RealmRecyclerViewAdapter<Playerafter,PlayerRealmAdapter.PlayerViewHolder> {

    Context context;



    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        protected TextView playername;
        protected CardView cardView;
        protected RecyclerView toprecycler;
        protected RecyclerView bottomrecycler;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            playername = (TextView) itemView.findViewById(R.id.textView4);
            cardView = (CardView) itemView.findViewById(R.id.backnamecardview);
            toprecycler = (RecyclerView) itemView.findViewById(R.id.recycler);
            bottomrecycler = (RecyclerView) itemView.findViewById(R.id.recycler2);
        }
    }
    public PlayerafterRealmAdapter(@NonNull Context context,@Nullable OrderedRealmCollection<Playerafter> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.context = context;
    }
    @Override
    public PlayerRealmAdapter.PlayerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_player, parent, false);
        final PlayerRealmAdapter.PlayerViewHolder holder = new PlayerRealmAdapter.PlayerViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(PlayerRealmAdapter.PlayerViewHolder holder, int position) {
        Playerafter player = getData().get(position);

        if (player.getPlayercolor() == 2) {
            holder.cardView.setBackgroundColor(Color.parseColor("#e6b422"));
        } else if (player.getPlayercolor() == 3) {
            holder.cardView.setBackgroundColor(Color.parseColor("#eb6101"));
        } else if (player.getPlayercolor() == 4) {
            holder.cardView.setBackgroundColor(Color.parseColor("#007bbb"));
        } else if (player.getPlayercolor() == 5) {
            holder.cardView.setBackgroundColor(Color.parseColor("#3f312b"));
            holder.playername.setTextColor(Color.WHITE);
        }

        holder.playername.setText(player.getPlayername());




    }


}
