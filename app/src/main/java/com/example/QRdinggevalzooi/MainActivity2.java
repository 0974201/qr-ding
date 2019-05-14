package com.example.QRdinggevalzooi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.WriterException;

public class MainActivity2 extends AppCompatActivity {

    //copypasta alles uit mainactivity how much can go wrong here lel

    private TextView textDing;
    private Button arse;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.textDing = findViewById(R.id.textDing);
        this.arse = findViewById(R.id.arse); //yeey it found the butt

        arse.setOnClickListener(new View.OnClickListener() { //kjken of er op het knopje is gedrukt
            @Override
            public void onClick(View v) {


                try {

                    textDing.setText("a");

                } catch (WriterException e) {

                    e.printStackTrace();
                }
            }
        });
    }
}
