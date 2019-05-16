package com.example.QRdinggevalzooi;

import android.hardware.camera2.CameraDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;

public class MainActivity2 extends AppCompatActivity {

    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    //bcdefghijklmnopqrstuvwdxhfjdsk stop procrastinating.
    //hoe heb ik de afgelopen paar dagen met letterlijk minder dan 3 uur slaap volgehouden idek it remains a mystery to me
    //also feel like yelling. misschien moet ik weer naar een paar arme koeien gaan schreeuwen hahahajkhdfjhfdk
    //of misschien niet bc my lungs hurt for 3 days or smth after last time. do not recommend.

    private TextView textDing;
    private Button arse;
    //private CameraDevice cameraDevice;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.textDing = findViewById(R.id.textDing);
        this.arse = findViewById(R.id.arse); //yeey it found the butt

        arse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanarYUVLuminanceSource sauce = new PlanarYUVLuminanceSource(0, 0, 0, 0, 0, 0, 0, false);

                //dit ding doet decoderen versnellen want negeren onnodige pixel dingen en werkt met camera driv?,,
                //mkea rry mt ifno v sauc
                //woorden
                //ik vertaal deez comments ltaer wle hfsjk
                if (sauce != null) {
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
