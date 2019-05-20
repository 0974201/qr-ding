package com.example.QRdinggevalzooi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity2 extends AppCompatActivity {

    //<< s c r e a m s  i n t o  t h e  v o i d >>

    private TextView textDing;
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
                Intents it = new Intents("com.google.zxing.client.android.SCAN");
                it.initiateScan();
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent it){
        Intentr scanRes = IntentIntegrator.parseActivityResult(reqCode, resCode, it);
        if(scanRes != null){
            System.out.println("yeet");
        }
    }

    /*private void ding(){
        //wat heb ik allemaal geschreven yo, ik kan mijn eigen comments niet lezen
        PlanarYUVLuminanceSource sauce = new PlanarYUVLuminanceSource(0,0,0,0,0,0,0,false);

        //dit ding doet decoderen versnellen want negeren onnodige pixel dingen en werkt met camera driv?,,
        //mkea rry mt ifno v sauc
        //woorden
        //ik vertaal deez comments ltaer wle hfsjk

        if(sauce != null){
            BinaryBitmap plaatje = new BinaryBitmap(new HybridBinarizer(sauce));
            //n dtea word t iern gstoep sndota qrsqnding hete kna vertlen nr stenfg me thg??
            //hfeeeeeeee
            //teyP
            try {
                textDing.setText(new qrScanDing().decoded(plaatje)); //wfjooooo lefkjf if s wokr
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        }
    }*/

}
