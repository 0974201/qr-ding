package com.example.qrzooi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

public class MainActivity extends AppCompatActivity {

    //waarom heeft dit ding een spellchecker. leaf me be.
    //also, waarom gebruikt dit ding letterlijk >2GB ram. android studio why
    //idc about those 4 warnings shhhh @ xml zooi

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
                String geweldigeTekst = dingetje.getText().toString().trim(); //maakt van de ingevoerde text een string - spaties op het begin/einde.

                try {
                    Bitmap yeet = new qrDing().qrDing(geweldigeTekst); //string -> qr code
                    ivStats.setImageBitmap(yeet); // -> laat qr code zien als het goed gaat

                } catch (WriterException e) {
                    //Toast.makeText(getApplicationContext(), "u done goofed", Toast.LENGTH_SHORT).show(); //?? moet nog uitzoeken hoe dit precies werkt lel.
                    e.printStackTrace(); //gooit een exception naar je.
                    Log.wtf(e.getMessage(), e);
                }
            }
        });
    }
}
