package com.example.idek;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

//ik haat mijzelf dus daarom maak ik een camera ding met een api dat nog niet eens in de beta stage is
//en waarvan de tutorial in een taal is dat ik 0% begrijp
//saus: https://codelabs.developers.google.com/codelabs/camerax-getting-started/

public class qrReaderFragment extends AppCompatActivity {
    ///<< s c r e a m s  i n t o  t h e  v o i d >>

    private TextView textDing;
    private Button knopje;
    private Handler cameraHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qr_reader);

        this.textDing = findViewById(R.id.textDing);
        this.knopje = findViewById(R.id.knopj);
        cameraHandler = new Handler();
        knopje.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ts = getSupportFragmentManager().beginTransaction(); //switch naar de camera
                ts.replace(R.id.qrReaderContainer, new CameraFragment());
                ts.addToBackStack(null);
                ts.commit();
            }
        });
    }

    @Override
    public void onActivityResult(int req, int rslt, Intent it) { //verwerkt wat er uit de scanding is gekomen
        super.onActivityResult(req, rslt, it);

        if (rslt == RESULT_OK && req == 1) {
            if (it != null) {
                if (it.hasExtra("res")) {
                    textDing.setText(it.getExtras().toString());
                    Log.wtf(String.valueOf(this), "scanned.");
                } else {
                    Log.wtf(String.valueOf(this), "phail");
                }
            } else {
                System.out.println("u done goofed.");
            }
        }
    }
}

