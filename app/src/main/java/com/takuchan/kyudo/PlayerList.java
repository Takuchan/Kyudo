package com.takuchan.kyudo;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.takuchan.kyudo.R;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

public class PlayerList extends DialogFragment {
    Realm realm;
    private ListView listView;
    private TextView homefragmenttext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_player_list, container, false);
        realm = Realm.getDefaultInstance();
        listView = (ListView)root.findViewById(R.id.playerlist);
        homefragmenttext = (TextView)root.findViewById(R.id.textView3);
        RealmResults<Player> realmResults = realm.where(Player.class).findAll();
        PlayerListAdapter adapter = new PlayerListAdapter(realmResults);
        listView.setAdapter(adapter);


        return  root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
