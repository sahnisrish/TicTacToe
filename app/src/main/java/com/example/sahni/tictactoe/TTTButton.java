package com.example.sahni.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;

/**
 * Created by sahni on 30/1/18.
 */

public class TTTButton extends AppCompatButton {
    private int Player;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    public TTTButton(Context context,int size)
    {
        super(context);
        Player= MainActivity.NO_PLAYER;
        setBackgroundResource(R.drawable.ttt_button);
        setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        setTextSize(TypedValue.COMPLEX_UNIT_SP,28+(5-size)*6);
    }
    public void setPlayer(int Player){
        this.Player=Player;
        if(Player== MainActivity.PLAYER_0) {
            setText("O");
        }
        else if(Player== MainActivity.PLAYER_X) {
            setText("X");
        }
        setBackgroundResource(R.drawable.ttt_button_selected);
        setEnabled(false);
    }

    public int getPlayer() {
        return Player;
    }
}