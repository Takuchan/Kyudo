package com.takuchan.kyudo;

import android.content.ClipData;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class PlayerRealmAdapter extends RealmRecyclerViewAdapter<Player,PlayerRealmAdapter.PlayerViewHolder> {

    Context context;
    static String number;




    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        protected TextView playername;
        protected CardView cardView;
        protected RecyclerView toprecycler;
        protected RecyclerView bottomrecycler;
        Realm realm;


        public PlayerViewHolder(View itemView) {
            super(itemView);
            playername = (TextView) itemView.findViewById(R.id.textView4);
            cardView = (CardView) itemView.findViewById(R.id.backnamecardview);
            toprecycler = (RecyclerView) itemView.findViewById(R.id.recycler);
            bottomrecycler = (RecyclerView) itemView.findViewById(R.id.recycler2);
            realm = Realm.getDefaultInstance();



        }
    }

    public PlayerRealmAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Player> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.context = context;

    }

    @Override
    public PlayerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_player, parent, false);
        final PlayerViewHolder holder = new PlayerViewHolder(itemView);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = holder.getAdapterPosition();
                number = String.valueOf(position);
                holder.cardView.setBackgroundResource(R.drawable.frame_square);
                holder.playername.setTextColor(Color.BLACK);

                holder.realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Player player = getData().get(position);
                        Number maxId = realm.where(Playerafter.class).max("id");
                        long nextId = 0;
                        if(maxId != null) nextId = maxId.longValue() + 1;
                        Playerafter playerafter = realm.createObject(Playerafter.class,new Long(nextId));
                        playerafter.setPlayername(player.getPlayername());
                        playerafter.setPlayercolor(player.getPlayercolor());
                        player.deleteFromRealm();
                    }
                });


                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = getData().get(position);

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
