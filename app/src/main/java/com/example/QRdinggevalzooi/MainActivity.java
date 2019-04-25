package com.example.QRdinggevalzooi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lol.R;

public class MainActivity extends AppCompatActivity {

    //waarom heeft dit ding een spellchecker. leaf me be.
    //todo: schrijf stuff idek
    //oh, nice ik wist niet dat android studio dat deed.
    //also, waarom gebruikt het letterlijk >2GB ram. android studio why

    private ImageView ivStats;
    private EditText dingetje;
    private Button butt;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ivStats = findViewById(R.id.ivStats);
        this.dingetje = findViewById(R.id.dingetje);
        this.butt = findViewById(R.id.butt); //yeey it found the butt

        butt.setOnClickListener(new View.OnClickListener() { //kjken of er op het knopje is gedrukt
            @Override
            public void onClick(View v) {
                String output = dingetje.getText().toString().trim(); //maakt van de ingevoerde text een string - spaties op het begin/einde.
                //ivStats.setImageBitmap(yeet);
                //oke en wat moet ik nu doen.
                //of ik geef het op en dump alles in de main activity want ughhh ik heb maar 2 uur geslapen rip.
            }
        });
    }
}
