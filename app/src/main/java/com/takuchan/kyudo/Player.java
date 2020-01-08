package com.takuchan.kyudo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {
    @PrimaryKey
    private long id;
    private String playername;
    private int playercolor;

    public int getPlayercolor() {
        return playercolor;
    }

    public void setPlayercolor(int playercolor) {
        this.playercolor = playercolor;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }



}
