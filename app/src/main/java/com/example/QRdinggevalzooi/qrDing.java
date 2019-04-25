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
//import com.google.zxing.qrcode.*;

public class qrDing{

    //ze hebben de code v/d test app uit de zxing repo verwijderd. cool. to the waybackmachine!
    //waybackmachine heb het ook niet lol
    //jfsdlkfjslkdjflkdfjlkdjf ik ga mezelf van het dak yeeten
    //todo: yeet mezelf van het dak
    //https://zxing.github.io/zxing/apidocs/ !!! apidocs yessssss <3
    
    public static Bitmap qrDing(String geweldigeTekst) throws WriterException {

        QRCodeWriter qr = new QRCodeWriter(); //de writer aanroepen om qr dingetje te maken.

        int qrWH = 350; //hoe groot de qr code moet zijn in pixels

        HashMap<EncodeHintType, Object> encodeding = new HashMap<EncodeHintType, Object>();
        //econdjhintype in hashmap gestopt, geef object mee zodat die de andere shit pakt.
        encodeding.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q); //geeft error correctie mee, level q is 25% ofzo iets met correctie in qr codes moet dit nog uitzoeken op wiki ofzo
        encodeding.put(EncodeHintType.MARGIN, 2); //maakt dat witte vlak ding om de qr code kleiner

        try {
            BitMatrix maakQRDing = qr.encode(geweldigeTekst, BarcodeFormat.QR_CODE, qrWH, qrWH, encodeding);
            //hm, oke let's see, de string geeft mee wat er encoded moet worden,
            //BarcodeFormat in welke format het moet (bijv qr, maar kan ook een barcode zijn),
            //width en height spreken voor zich
            //encodeding neemt extra shit mee zoals error correctie of wlke soort encoding (utf-8/shift-js) je wilt gebruiken

            int[] zooi = new int[qrWH * qrWH]; //array aanmaken om de qr erin te proppen
            for (int i = 0; i < qrWH; i++){
                for (int j = 0; j < qrWH; j++){
                    if (maakQRDing.get(j,i)){ //kijkt naar huidige bit en als het true is dan wordt ie zwart
                        zooi[i * qrWH + j] = Color.BLACK;
                    } else {
                        zooi[i * qrWH + j] = Color.WHITE;
                    }
                }
            }

            Bitmap yeet = Bitmap.createBitmap(qrWH, qrWH, Bitmap.Config.ARGB_8888); //qr code > bitmap zodat het gezien kan worden
            yeet.setPixels(zooi, 0, qrWH, 0, 0, qrWH, qrWH); //uh schijnbaar zorgt dit ervoor dat de qr code scherp is??
            return yeet;

        } catch (WriterException ex){
            ex.printStackTrace(); //gooit een exception naar je kop als je iets doms doet.
        }
        return null;
    }
}
