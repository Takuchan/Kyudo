package com.takuchan.kyudo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.takuchan.kyudo.ui.home.HomeViewModel;

import io.realm.Realm;

public class Addplayer extends DialogFragment {

    private Realm realm;
    private Button addplayerbutton;
    private EditText playername;
    private Button white;
    private Button yellow;
    private Button red;
    private Button blue;
    private Button gray;

    int playercolor;
    String playercolorstring;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_addplayer, container, false);

        playercolor = 0;
        playercolorstring = "無色";
        realm = Realm.getDefaultInstance();
        addplayerbutton = (Button)root.findViewById(R.id.button11);
        playername = (EditText) root.findViewById(R.id.editText);
        white = (Button)root.findViewById(R.id.button6);
        yellow = (Button)root.findViewById(R.id.button7);
        red = (Button)root.findViewById(R.id.button9);
        blue = (Button)root.findViewById(R.id.button8);
        gray = (Button)root.findViewById(R.id.button10);

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playercolor = 2;
                playercolorstring = "黄金";
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playercolor = 3;
                playercolorstring = "朱色";
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playercolor = 4;
                playercolorstring = "紺碧";
            }
        });
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playercolor = 5;
                playercolorstring = "赤墨";
            }
        });

        addplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String playernames = playername.getText().toString();
                if (playernames.length() == 0){
                    Toast.makeText(getActivity(), "名前を入力してください。", Toast.LENGTH_SHORT).show();
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage(playercolorstring +playernames + "を登録しますか")
                            .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            Number maxId = realm.where(Player.class).max("id");
                                            long nextId = 0;
                                            if(maxId != null) nextId = maxId.longValue() + 1;
                                            Player player = realm.createObject(Player.class,new Long(nextId));
                                            player.setPlayername(playernames);
                                            player.setPlayercolor(playercolor);
                                            Toast.makeText(getContext(), playernames + "を登録しました", Toast.LENGTH_SHORT).show();
                                            playername.setText("");

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("キャンセル", null)
                            .show();
                }

            }
        });
        return root;

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
