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

public class qrgen{

    //ze hebben de code v/d test app uit de zxing repo verwijderd. cool. to the waybackmachine!
    //waybackmachine heb het ook niet lol
    //jfsdlkfjslkdjflkdfjlkdjf ik ga mezelf van het dak yeeten
    //todo: yeet mezelf van het dak
    //https://zxing.github.io/zxing/apidocs/ !!! apidocs yessssss <3

    //variabelen:
    //private int SIZE = 350; //hoe groot de qr code moet zijn in pixels

    public static Bitmap qrDing(String geweldigeTekst) throws WriterException {

        QRCodeWriter qr = new QRCodeWriter(); //de writer aanroepen om qr dingetje te maken.

        //geweldigeTekst = "aaaa"; // <- meegeven wat er in een textbox wordt ingevoerd.
        int size = 350;
        int width = size;
        int height = size;

        HashMap<EncodeHintType, Object> encodeding = new HashMap<EncodeHintType, Object>();
        //econdjhintype in hashmap gestopt, geef object mee zodat die de andere shit pakt.
        encodeding.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q); //geeft error correctie mee, level q is 25% ofzo iets met correctie in qr codes moet dit nog uitzoeken op wiki ofzo
        encodeding.put(EncodeHintType.MARGIN, 3); //maakt dat witte vlak ding om de qr code kleiner

        try {
            BitMatrix maakQRDing = qr.encode(geweldigeTekst, BarcodeFormat.QR_CODE, width, height, encodeding);
            //hm, oke let's see, de string geeft mee wat er encoded moet worden,
            //BarcodeFormat in welke format het moet (bijv qr, maar kan ook een barcode zijn),
            //width en height spreken voor zich
            //encodeding neemt extra shit mee zoals error correctie of wlke soort encoding (utf-8/shift-js) je wilt gebruiken

            int[] zooi = new int[size * size]; //array aanmaken om de qr erin te proppen
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    if (maakQRDing.get(j,i)){ //kijkt naar huidige bit en als het true is dan wordt ie zwart
                        zooi[i * size + j] = Color.BLACK;
                    } else {
                        zooi[i * size + j] = Color.WHITE;
                    }
                }
            }

            Bitmap yeet = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888); //qr code > bitmap zodat het gezien kan worden
            yeet.setPixels(zooi, 0, size, 0, 0, size, size); //uh schijnbaar zorgt dit ervoor dat de qr code scherp is??
            return yeet;

        } catch (WriterException ex){
            ex.printStackTrace(); //gooit een exception naar je kop als je iets doms doet.
        }
        return null;
    }






}
