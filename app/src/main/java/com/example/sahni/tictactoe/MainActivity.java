package com.example.sahni.tictactoe;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int PLAYER_X=1;
    public static final int PLAYER_0=2;
    public static final int NO_PLAYER=-1;

    public static String gameStatus;
    LinearLayout rootLayout;
    TextView Player1;
    TextView Player2;

    static int currentPlayer;
    static String currentPlayerName;
    TTTButton board[][];
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        rootLayout=findViewById(R.id.root);
        Player1=findViewById(R.id.player_1);
        Player2=findViewById(R.id.player_2);

        if(!bundle.getString(PlayerDetails.PLAYER_ONE_KEY).equals(""))
            Player1.setText(bundle.getString(PlayerDetails.PLAYER_ONE_KEY));
        if(!bundle.getString(PlayerDetails.PLAYER_TWO_KEY).equals(""))
            Player2.setText(bundle.getString(PlayerDetails.PLAYER_TWO_KEY));
        inItBoard();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void inItBoard() {
        board=new TTTButton[PlayerDetails.size][PlayerDetails.size];
        currentPlayer=PLAYER_X;
        currentPlayerName=Player1.getText().toString();
        gameStatus="Draw";
        createBoard();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createBoard() {
        rootLayout.removeAllViews();
        for(int i=0;i<PlayerDetails.size;i++){
            LinearLayout row=new LinearLayout(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            row.setLayoutParams(params);
            for(int j=0;j<PlayerDetails.size;j++){
                TTTButton button=new TTTButton(this,PlayerDetails.size);
                LinearLayout.LayoutParams paramsButton=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(paramsButton);
                board[i][j]=button;
                button.setOnClickListener(this);
                row.addView(button);
            }
            rootLayout.addView(row);
        }
        Toast.makeText(this,currentPlayerName+"'s Turn",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        TTTButton button=(TTTButton)v;
        button.setPlayer(currentPlayer);
        Test();
        if(isFull()) {
            if(gameStatus.equals("Draw"))
                for(int i=0;i<PlayerDetails.size;i++)
                    for(int j=0;j<PlayerDetails.size;j++)
                        board[i][j].setBackgroundResource(R.drawable.end);
            Toast.makeText(this, gameStatus, Toast.LENGTH_SHORT).show();
        }
        else
            swap();
    }

    private boolean isFull() {
        for(int i=0;i<PlayerDetails.size;i++)
            for(int j=0;j<PlayerDetails.size;j++)
                if(board[i][j].isEnabled())
                    return false;
        return true;
    }

    private void swap() {
        if (currentPlayer == PLAYER_0) {
            currentPlayer = PLAYER_X;
            currentPlayerName=Player1.getText().toString();
        }
        else {
            currentPlayer = PLAYER_0;
            currentPlayerName=Player2.getText().toString();
        }
    }

    private void Test() {
        //Every row
        for(int i=0;i<PlayerDetails.size;i++)
        {
            boolean isSame=true;
            if(board[i][0].getPlayer()==NO_PLAYER)
                isSame=false;
            else {
                for (int j = 0; j < PlayerDetails.size; j++)
                    if (board[i][j].getPlayer() != board[i][0].getPlayer())
                        isSame = false;
            }
            if(isSame)
            {
                for(int j=0;j<PlayerDetails.size;j++)
                    board[i][j].setBackgroundResource(R.drawable.won);
                PlayerWon();
                return;
            }
        }
        //Every column
        for(int j=0;j<PlayerDetails.size;j++)
        {
            boolean isSame=true;
            if(board[0][j].getPlayer()==NO_PLAYER)
                isSame=false;
            else {
                for (int i = 0; i < PlayerDetails.size; i++)
                    if (board[i][j].getPlayer() != board[0][j].getPlayer())
                        isSame = false;
            }
            if(isSame)
            {
                for(int i=0;i<PlayerDetails.size;i++)
                    board[i][j].setBackgroundResource(R.drawable.won);
                PlayerWon();
                return;
            }
        }
        //Diagonal 1
        boolean isSame=true;
        if(board[0][0].getPlayer()==NO_PLAYER)
            isSame=false;
        else
            for(int i=0;i<PlayerDetails.size;i++)
                if(board[i][i].getPlayer()!=board[0][0].getPlayer())
                    isSame=false;
        if(isSame)
        {
            for(int i=0;i<PlayerDetails.size;i++)
                board[i][i].setBackgroundResource(R.drawable.won);
            PlayerWon();
            return;
        }
        //Diagonal 2
        isSame=true;
        if(board[0][PlayerDetails.size-1].getPlayer()==NO_PLAYER)
            isSame=false;
        else
            for(int i=0;i<PlayerDetails.size;i++)
                if(board[i][PlayerDetails.size-1-i].getPlayer()!=board[0][PlayerDetails.size-1].getPlayer())
                    isSame=false;
        if(isSame)
        {
            for(int i=0;i<PlayerDetails.size;i++)
                board[i][PlayerDetails.size-1-i].setBackgroundResource(R.drawable.won);
            PlayerWon();
            return;
        }
    }
    private void PlayerWon() {
        for(int i=0;i<PlayerDetails.size;i++)
            for(int j=0;j<PlayerDetails.size;j++)
                board[i][j].setEnabled(false);
        gameStatus=currentPlayerName+" Wins!";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = null;
        if(PlayerDetails.size==3)
            item=menu.findItem(R.id.three);
        else if(PlayerDetails.size==4)
            item=menu.findItem(R.id.four);
        else if(PlayerDetails.size==5)
            item=menu.findItem(R.id.five);
        item.setChecked(true);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.reset)
            inItBoard();
        else if(id==R.id.three){
            PlayerDetails.size=3;
//            setContentView(R.layout.activity_player_details);
//            PlayerDetails.Default=findViewById(R.id.button3);
//            PlayerDetails.Default.setChecked(true);
//            setContentView(R.layout.activity_main);
            item.setChecked(true);
            inItBoard();
        }
        else if(id==R.id.four){
            PlayerDetails.size=4;
//            setContentView(R.layout.activity_player_details);
//            PlayerDetails.Default=findViewById(R.id.button4);
//            PlayerDetails.Default.setChecked(true);
//            setContentView(R.layout.activity_main);
            item.setChecked(true);
            inItBoard();
        }
        else if(id==R.id.five){
            PlayerDetails.size=5;
//            setContentView(R.layout.activity_player_details);
//            PlayerDetails.Default=findViewById(R.id.button5);
//            PlayerDetails.Default.setChecked(true);
//            setContentView(R.layout.activity_main);
            item.setChecked(true);
            inItBoard();
        }
        return super.onOptionsItemSelected(item);
    }
}
