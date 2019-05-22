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
                IntentIntegrator it = new IntentIntegrator(MainActivity2.this); //roept qr scanner van de geimporteerde library
                it.initiateScan(IntentIntegrator.QR_CODE_TYPES); //zoekt naar een qr code
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
}
