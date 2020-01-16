package com.takuchan.kyudo.ui.home;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private Button playplayer;
    private RecyclerView listplayerrecyler,listplayerrecyclerafter;
    private Spinner playcount,playercutofpeople,playofshot;
    private LinearLayout playercontroll;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        playplayer = (Button)root.findViewById(R.id.playplayeradd);
        listplayerrecyler = (RecyclerView)root.findViewById(R.id.recycler);
        listplayerrecyclerafter =(RecyclerView)root.findViewById(R.id.recycler2);
        playercontroll = (LinearLayout)root.findViewById(R.id.playcont);


        //Sppinerのfindviewby
        playcount = (Spinner)root.findViewById(R.id.spinner3);
        playercutofpeople = (Spinner)root.findViewById(R.id.spinner4);
        playofshot = (Spinner)root.findViewById(R.id.spinner5);
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
        final RealmResults<Playerafter> realmResult1 = realm.where(Playerafter.class).findAll();
        final PlayerafterRealmAdapter realmAdapter = new PlayerafterRealmAdapter(getActivity(),realmResult1,true);
        listplayerrecyclerafter.setAdapter(realmAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.UP) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        realmAdapter.notifyItemMoved(fromPos, toPos);

                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                //したのRecyclerViewから要素を削除する
                                Playerafter playerafter = realmResult1.get(fromPos);
                                final String playername = playerafter.getPlayername();
                                final int playercolor = playerafter.getPlayercolor();
                                playerafter.deleteFromRealm();
                                //したから上のRecyclerVIewに要素を送信する
                                Number maxId = realm.where(Player.class).max("id");
                                long nextId = 1;
                                if (maxId != null) {
                                    nextId = maxId.longValue() + 1;
                                }
                                Player player = realm.createObject(Player.class, new Long(nextId));
                                player.setPlayername(playername);
                                player.setPlayercolor(playercolor);
                            }
                        });

                    }
                });
        mIth.attachToRecyclerView(listplayerrecyclerafter);



        //立を始めるボタン
        playplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playcounttatchi = (String)playcount.getSelectedItem();
                String cutplayer = (String)playercutofpeople.getSelectedItem();
                String playerofshot = (String)playofshot.getSelectedItem();
                if (realmResult1.size() == 0){
                    Toast.makeText(getContext(), "下の空白に名札を挿入してください", Toast.LENGTH_LONG).show();
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("立を始めます")
                            .setMessage("下の空白に配置した名札順に立を始めます\n"+"立の数"+playcounttatchi + "\n区切り人数"+cutplayer+ "\n一つに立に射込む数"+ playerofshot)
                            .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    
                                }
                            })
                            .setNegativeButton("戻る", null)
                            .show();
                }

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