package com.takuchan.kyudo.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.takuchan.kyudo.Addplayer;
import com.takuchan.kyudo.Player;
import com.takuchan.kyudo.PlayerList;
import com.takuchan.kyudo.PlayerRealmAdapter;
import com.takuchan.kyudo.Playerafter;
import com.takuchan.kyudo.PlayerafterRealmAdapter;
import com.takuchan.kyudo.R;
import io.realm.Realm;
import io.realm.RealmResults;


public class HomeFragment extends Fragment {

    private Realm realm;
    private HomeViewModel homeViewModel;
    private TableLayout tableLayout;
    private Button addplayer,playerlist,playplayer;
    private RecyclerView listplayerrecyler,listplayerrecyclerafter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addplayer = (Button)root.findViewById(R.id.addplayerc);
        playerlist = (Button)root.findViewById(R.id.button12);
        playplayer = (Button)root.findViewById(R.id.playplayeradd);
        listplayerrecyler = (RecyclerView)root.findViewById(R.id.recycler);
        listplayerrecyclerafter =(RecyclerView)root.findViewById(R.id.recycler2);

        realm = Realm.getDefaultInstance();
        //ActionBarの有効か
        setHasOptionsMenu(true);

        //Recyclerにプレーヤーを一覧で表示する
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listplayerrecyler.setLayoutManager(llm);
        RealmResults<Player> realmResults = realm.where(Player.class).findAll();
        final PlayerRealmAdapter playerRealmAdapter = new PlayerRealmAdapter(getActivity(),realmResults,true);
        listplayerrecyler.setAdapter(playerRealmAdapter);

        //下のRecyclerに立に追加したプレーヤを一覧で表示する
        LinearLayoutManager llm2 = new LinearLayoutManager(getContext());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        listplayerrecyclerafter.setLayoutManager(llm2);
        RealmResults<Playerafter> realmResult1 = realm.where(Playerafter.class).findAll();
        final PlayerafterRealmAdapter realmAdapter = new PlayerafterRealmAdapter(getActivity(),realmResult1,true);
        listplayerrecyclerafter.setAdapter(realmAdapter);




        //立を始めるボタン
        playplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        tableLayout = (TableLayout)root.findViewById(R.id.tablelayout);
        TableRow tableRow = new TableRow(getActivity());
        tableLayout.addView(tableRow);
        tableRow.addView(PointButton());


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.home_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.addplayerc:
                //選手追加
                Addplayer addplayer = new Addplayer();
                addplayer.show(getFragmentManager(), "");
                break;
            case R.id.button12:
                //選手一覧
                PlayerList playerList = new PlayerList();
                playerList.show(getFragmentManager(), "");
                break;
        }
        return true;
    }
    private ImageButton PointButton(){
        ImageButton imageButton = new ImageButton(getActivity());
        imageButton.setImageResource(R.drawable.ic_add_black_24dp);
        return imageButton;
    }

}