package com.example.qrzooi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.integration.IntentIntegrator;
import com.google.zxing.client.android.integration.IntentResult;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraDing.newInstance())
                    .commit();
        }
    }


    //<< s c r e a m s  i n t o  t h e  v o i d >>

    /*private TextView textDing;
    private Button arse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.textDing = findViewById(R.id.textDing);
        this.arse = findViewById(R.id.arse);

        arse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Intent it = new Intent("com.google.zxing.client.android.SCAN");
                //it.putExtra("SCAN_MODE", "QR_CODE_MODE");

                IntentIntegrator it = new IntentIntegrator(MainActivity2.this); //roept qr scanner van de geimporteerde library
                it.initiateScan(IntentIntegrator.QR_CODE_TYPES, -1); //zoekt naar een qr code
                //it.initiateScan();
            }
        });
    }


    @Override
    public void onActivityResult(int req, int rslt, Intent it){ //verwerkt wat er uit de scanding is gekomen

        super.onActivityResult(req, rslt, it);

        IntentResult res = IntentIntegrator.parseActivityResult(req, rslt, it);

        if(res != null){
            if(res.getContents() != null){
                textDing.setText(res.toString());
                textDing.setText(res.getContents());
                Log.wtf(String.valueOf(this), "scanned.");
                Toast.makeText(this, "scanned", Toast.LENGTH_SHORT);
            } else{
                Log.d(String.valueOf(this),"phail");
                Toast.makeText(this, "phail", Toast.LENGTH_SHORT);
            }
        } else {
            System.out.println("u done goofed.");
        }
    }*/




}
