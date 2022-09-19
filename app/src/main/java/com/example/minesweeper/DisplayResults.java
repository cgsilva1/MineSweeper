package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        //get the intent and gets the message from it using .getStringExtra()
        Intent intent = getIntent();
        String message = intent.getStringExtra("com.example.cellClick.MESSAGE");
        String time = intent.getStringExtra("com.example.cellClick.TIME");

        message = "Used " + time +" seconds.\n" + message ; //appending fight on to the message received

        TextView textView = (TextView) findViewById(R.id.Message);
        textView.setText(message); //add the text to the message text view
    }

    public void playAgain(View view) { //go back to main page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}