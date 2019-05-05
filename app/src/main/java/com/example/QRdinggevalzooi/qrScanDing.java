package com.example.QRdinggevalzooi;

import com.google.zxing.Result;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

import java.util.Map;

public class qrScanDing {
    //stop met updates android studio pl0x
    //ok let's copypasta stuff out of the documentation first so i don't have to switch teh entire time
    //https://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/QRCodeReader.html

    public final Result decode(BinaryBitmap image, Map<DecodeHintType,?> hints) throws NotFoundException, ChecksumException, FormatException {
        //dat zijn een heleboel exceptions bro, moet later uitzoeken wat het allemaal doet lol
        //???nfc je kan ook nfc zooi doen??

        try{
            return new Result();
        } catch (NotFoundException nf){
            //aaaaaaaah oke wat doe ik met de rest.
            nf.printStackTrace();
        }
        return null;
    }
}
