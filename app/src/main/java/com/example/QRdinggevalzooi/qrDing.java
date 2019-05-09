package com.example.QRdinggevalzooi;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

public class qrDing{

    //ze hebben de code v/d test app uit de zxing repo verwijderd. cool. to the waybackmachine!
    //waybackmachine heb het ook niet lol
    //todo: yeet
    //https://zxing.github.io/zxing/apidocs/ !!! apidocs yessssss <3
    //https://developer.android.com/reference/android/graphics/Color?hl=en
    //food for thought: http://datagenetics.com/blog/november12013/index.html

    //wedden dat als ik later hier naar ga kijken, + medicatie niet ben vergeten, mij serieus ga afvragen wtf ik dit alles heb aangepast

    private int qrWH = 350; //hoe groot de qr code moet zijn in pixels
    private BarcodeFormat barF = BarcodeFormat.QR_CODE; //ehehehe. geeft aan bitmatrix mee in welke format het moet encoderen (bijv qr, maar kan ook een barcode zijn)
    private int kleurtje = Color.rgb(204, 1, 51); //rood. now, let's see if this will work. it does!!
    private int kleurtje2ElectricBoogaloo = Color.WHITE; // en dit is de kleur wit. verder geen bijzondere opmerkingen

    public Bitmap qrDing(String geweldigeTekst) throws WriterException { // < hoe had ik met mijn slaperige kop de static gemist hier??

        QRCodeWriter qr = new QRCodeWriter(); //de writer aanroepen om qr dingetje te maken.

        HashMap<EncodeHintType, Object> encodeDing = new HashMap<EncodeHintType, Object>();
        //econdjhintype in hashmap gestopt, geef object mee zodat die de andere shit pakt.
        encodeDing.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q); //geeft error correctie mee, level q is 25% correctie
        encodeDing.put(EncodeHintType.MARGIN, 1); //maakt dat witte vlak ding om de qr code kleiner

        try {
            BitMatrix maakQRDing = qr.encode(geweldigeTekst, barF, qrWH, qrWH, encodeDing);
            //hm, oke let's see, de string geeft mee wat er encoded moet worden,
            //width en height spreken voor zich
            //encodeDing neemt extra shit mee zoals error correctie of welke soort encoding (utf-8/shift-js) je wilt gebruiken

            int[] zooi = new int[qrWH * qrWH]; //array voor bitmap ding zodat hij de qr codeding kan omzetten naar een plaatje :D
            //in andere woorden; de qr ding wordt bruut vermoord en in stukjes gezaagd en dan weer inelkaar gezet.
            for (int i = 0; i < qrWH; i++){
                for (int j = 0; j < qrWH; j++){
                    if (maakQRDing.get(j,i)){ //kijkt naar huidige bit en als het true is dan wordt ie zwart/// rood!!
                        zooi[i * qrWH + j] = kleurtje;
                    } else {
                        zooi[i * qrWH + j] = kleurtje2ElectricBoogaloo;
                    }
                }
            }

            Bitmap yeet = Bitmap.createBitmap(zooi, qrWH, qrWH, Bitmap.Config.ARGB_8888); //qr code > bitmap zodat het gezien kan worden
            //yeet.setPixels(zooi, 0, qrWH, 0, 0, qrWH, qrWH); //dit in comments gezet aangezien createBitmap de array ook lust.
            //@android studio, no u are redundant.
            return yeet;

        } catch (WriterException ex){

            ex.printStackTrace(); //nog meer exceptions
            //todo: uhhhh dit afhandelen aangezien hij exit als je geen shit hebt ingevoerd???????
        }
        return null;
    }
}
