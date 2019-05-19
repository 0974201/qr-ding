package com.example.QRdinggevalzooi;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    //bcdefghijklmnopqrstuvwdxhfjdsk stop procrastinating.
    //oof. okay dan maar kijken naar de cameradingvoorbeeld van google.
    //waarom dacht ik dat dit een goed idee was.
    //hoezo staat overal m voor? zal vast een conventie zijn maar het is 4 uur, heb geen zin om dat op te zoeken

    private TextView textDing;
    private Button arse;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.textDing = findViewById(R.id.textDing);
        this.arse = findViewById(R.id.arse);

        arse.setOnClickListener(new View.OnClickListener() {
            //wat heb ik allemaal geschreven yo, ik kan mijn eigen comments niet lezen
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*private void ding(){ //ooh nice, in de zxing github staat iets wat ik kan gebruiken :o
        //hhfdsjfk oke er is een voorbeeld op de github vn google hoe je een cameradinggeval maken doet
        //don't try to reinvent the wheel,,, volg jordys advies voor een keer, sushi.

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
