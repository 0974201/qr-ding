package com.example.qrzooi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.client.android.integration.IntentIntegrator;
import com.google.zxing.client.android.integration.IntentResult;

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
                IntentIntegrator it = new IntentIntegrator(MainActivity2.this); //roept qr scanner van de library
                //it.setSingleTargetApplication("com.google.zxing.client.android.SCAN");
                it.initiateScan(IntentIntegrator.QR_CODE_TYPES); //moet voor qr coeedes zoeken
            }
        });
    }

    public void onActivityResult(int reqCode, int rsCode, Intent it){ //verwerkt wat er uit de scanding is gekomen
        IntentResult iRes = IntentIntegrator.parseActivityResult(reqCode, rsCode, it);

        if(iRes != null){
            String resCont = iRes.getContents();

            if(resCont != null){
                //showDialog(R.string.result_succeeded, iRes.toString());
                System.out.println(resCont);
                System.out.println(iRes.toString());
                textDing.setText(resCont);
            }
        } else {
            //showDialog(R.string.result_failed, getString(R.string.result_failed_why)); //ide error ahoyyy
            System.out.println("phail");
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
