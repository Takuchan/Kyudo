package com.takuchan.kyudo;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class PlayerListAdapter extends RealmBaseAdapter<Player> {
    String colorname;
    public PlayerListAdapter(@Nullable OrderedRealmCollection<Player> data) {
        super(data);
    }


    private static class ViewHolder{
        TextView name;
        TextView color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(android.R.id.text1);
            viewHolder.color = (TextView)convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Player player = adapterData.get(position);
        if(player.getPlayercolor() == 1){
            colorname = "無色";

        }else if(player.getPlayercolor() == 2){
            colorname = "黄金";
            viewHolder.color.setTextColor(Color.parseColor("#e6b422"));
        }else if (player.getPlayercolor() == 3){
            colorname = "朱色";
            viewHolder.color.setTextColor(Color.parseColor("#eb6101"));
        }else if (player.getPlayercolor() == 4){
            colorname = "紺碧";
            viewHolder.color.setTextColor(Color.parseColor("#007bbb"));
        }else if(player.getPlayercolor() == 5){
            colorname = "赤墨";
            viewHolder.color.setTextColor(Color.parseColor("#3f312b"));
        }
        viewHolder.name.setText(player.getPlayername());
        viewHolder.color.setText(colorname);

        return convertView;
    }
}
