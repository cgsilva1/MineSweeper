package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnCellClickListener {
    public static final long TIMER_LENGTH = 99900L;
    public static final int BOMB_COUNT = 4;
    public static final int ROW_SIZE = 10;
    private static final int COLUMN_COUNT = 8; //need to have n+1 of columns number

    private MineGridRecyclerAdapter mineGridRecyclerAdapter;
    private RecyclerView grid;
    private TextView timer, flag, flagsLeft;
    private MineSweeperGame mineSweeperGame;
    private CountDownTimer countDownTimer;
    private int secondsElapsed;
    private boolean run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.activity_main_grid);
        grid.setLayoutManager(new GridLayoutManager(this, 10));

        flagsLeft = findViewById(R.id.textViewFLAGCOUNT); //count
        timer = findViewById(R.id.textViewCLOCK_COUNT); //count

        run = false;
        countDownTimer = new CountDownTimer(TIMER_LENGTH, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsElapsed += 1;
                timer.setText(String.format("%02d", secondsElapsed));
            }

            //NEED TO GO TO NEW PAGE
            public void onFinish() {
                mineSweeperGame.outOfTime();
//                mineSweeperGame.getMineGrid().showBombs(); //show all bombs
                mineGridRecyclerAdapter.setCells(mineSweeperGame.getMineGrid().getCells());
                mineSweeperGame.isLoose();
            }
        };

        if(!run) { //start timer automatically
            countDownTimer.start();
            run = true;
        }

        mineSweeperGame = new MineSweeperGame(ROW_SIZE, COLUMN_COUNT, BOMB_COUNT);
        flagsLeft.setText(String.format("%02d", mineSweeperGame.getNumberBombs() - mineSweeperGame.getFlagCount())); //calcualte the # of flags left
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(mineSweeperGame.getMineGrid().getCells(), this);
        grid.setAdapter(mineGridRecyclerAdapter);

        //FLAG-PICK Chooser
        flag = findViewById(R.id.activity_main_pick);
        flag.setOnClickListener(new View.OnClickListener() { //when click chagne to pick
            @Override
            public void onClick(View view) {
                mineSweeperGame.toggleMode(view);
//                flag = findViewById(R.id.activity_main_pick);
                if (mineSweeperGame.isFlagMode()) { //once clicked on flag can now flag cells
//                    flag = findViewById(R.id.activity_main_pick);
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0xFFFFFFFF);
                    border.setStroke(1, 0xFF000000); //setting flag
                    flag.setBackground(border);
                } else {
//                    flag = findViewById(R.id.activity_main_flag);
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0xFFFFFFFF);
                    flag.setBackground(border);
                }
            }
        });
    }

    @Override
    public void cellClick(Cell cell) {
        mineSweeperGame.handleCellClick(cell);

        flagsLeft.setText(String.format("%02d", mineSweeperGame.getNumberBombs() - mineSweeperGame.getFlagCount()));

        Intent intent = new Intent(this, DisplayResults.class);

        //go to new page & get time
        if (mineSweeperGame.isLoose()) {
            //CREATE MESSAGE SAYING "GAME LOST"
            String lostMessage = "You Lost \n Nice Try!";
            countDownTimer.cancel(); //stop timer
            String time = String.valueOf(secondsElapsed); //get time of when game is stopped

            intent.putExtra("com.example.cellClick.MESSAGE", lostMessage);
            intent.putExtra("com.example.cellClick.TIME", time);
            mineSweeperGame.getMineGrid().showBombs(); //reveal all bombs
            //need to wait for user to click again to redirect to new page
            startActivity(intent); //goes to results page

//            grid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(intent); //goes to results page
//                }
//            });


        }



        mineGridRecyclerAdapter.setCells(mineSweeperGame.getMineGrid().getCells());
    }
}