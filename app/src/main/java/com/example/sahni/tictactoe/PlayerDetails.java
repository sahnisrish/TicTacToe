package com.example.sahni.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class PlayerDetails extends AppCompatActivity {
    public static int size=3;
    final String P1_KEY="player_1";
    final String P2_KEY="player_2";
    EditText Player1;
    EditText Player2;
    SharedPreferences playerData;
    public static RadioButton Default;

    public static final String PLAYER_ONE_KEY="name1";
    public static final String PLAYER_TWO_KEY="name2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        Player1=findViewById(R.id.Player1name);
        Player2=findViewById(R.id.Player2name);

        playerData=getSharedPreferences("playerData", Context.MODE_PRIVATE);
        String p1=playerData.getString(P1_KEY,"");
        String p2=playerData.getString(P2_KEY,"");
        Player1.append(p1);
        Player2.append(p2);

        if(size==3)
            Default=findViewById(R.id.button3);
        else if(size==4)
            Default=findViewById(R.id.button4);
        else if(size==5)
            Default=findViewById(R.id.button5);
        Default.setChecked(true);
    }

    public void Choose(View view) {
        RadioButton radioButton=(RadioButton)view;
        if(radioButton.getText().toString().equals("3X3"))
            size=3;
        else if(radioButton.getText().toString().equals("4X4"))
            size=4;
        else if(radioButton.getText().toString().equals("5X5"))
            size=5;
    }

    public void Switch(View view) {
        String p1=Player1.getText().toString();
        String p2=Player2.getText().toString();

        SharedPreferences.Editor editor=playerData.edit();
        editor.putString(P1_KEY,p1);
        editor.putString(P2_KEY,p2);
        editor.commit();

        Intent intent=new Intent(this, MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(PLAYER_ONE_KEY,p1);
        bundle.putString(PLAYER_TWO_KEY,p2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
