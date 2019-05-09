package com.example.QRdinggevalzooi;

import com.google.zxing.ChecksumException;
import com.google.zxing.Result;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.HashMap;

public class qrScanDing {
    //stop met updates android studio pl0x ლ(¯ロ¯ლ)
    //ok let's copypasta stuff out of the documentation first so i don't have to switch teh entire time ᕕ( ᐛ )ᕗ
    //https://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/QRCodeReader.html

    public String decodeZzz(BinaryBitmap plaatje) throws NotFoundException, FormatException {
        //uuhhh wacht ik moet de camera... shit. ik dacht al, dit is minder exciting dan de qrgenerator dingetje
        QRCodeReader qr = new QRCodeReader(); // (*・ω・)ﾉ ghelleu QRCodeReader
        String qrResultDing; //alvast een string aanmaken waarin de uitgelezen zooi in gepropt kan worden
        //nederlands is moeilijk, uit de documentatie: "Returns String which the barcode encodes"

        /*  kan later gedelete worden, er zal vast wel in de camera class iets soort gelijks zijn? later opzoeken.
        HashMap<DecodeHintType,Object> decodeDing = new HashMap<>(); //qrDing had een hashmap dat encodeDing heette dus vanaf nu heet jij decodeDing :D
        //in decodeDing kan je shit proppen dat de decoder helpt tijdens het uitlezen van een qrcode, including telling it to try harder when it can't find shit
        //enige reden waarom ik het heb meegenomen uit de documentatie lel. (wedden dat als ik mijn medicatie heb genomen en wat meer wakker ben ik dit ga weghalen omdat het te moeilijk is idek)
        decodeDing.put(DecodeHintType.TRY_HARDER,Boolean.TRUE); //uh, moet later documentatie doornemen om te kijken wat het precies doet, for now i'm just adding the boolean bc it told me to do so
        //iig, het gaat meer tijd besteden aan het zoeken naar een qr code maar gaat wel ten koste van de snelheid.
        */

        try{
            Result resDing = qr.decode(plaatje); //wauw. zonder medicatie is mijn beste uitleg hiervoor: "magic happens (/￣ー￣)/~~☆’.･.･:★’.･.･:☆"
            //aight op basis van wat je allemaal kan vragen van Result, slaat hij ingelezen qr code in zijn geheel op.
            //je zou specifieke shit kunnen opvragen afhankelijk van welke type barcode je hebt ingescand, bijv de "result points" (waarvan ik gok dat het in een qr code de grote blokken kunnen zijn?)
            //en bytes en bits van wat er geencodeerd is in een qr code/aztec ding/barcode
            //mensen hadden echt honger toen ze de namen hiervoor bedachten huh. "nibble, bit, byte" zolang je toetsenbord maar vrij blijft van kruimels, i guess
            //alleen is dat alles niet echt relevant hier want ik wil een string terug hebben en Result kan dat ook teruggeven :D

            qrResultDing = resDing.getText(); //en dat doen wij hier! geeft de string wat in de qr code stond terug en stopt dat in de string wat ik eerder heb aangemaakt.

            return qrResultDing; //< sssh ik fix het later wel als ik minder slaperig ben en shit kan lezen < VERANDER GEWOON WAT VOOR SHIT HIJ MOET TERUG GEVEN PROBLEM FIXED.

        } catch (NotFoundException nf){

            //aaaaaaaah oke wat doe ik met de rest. NVM I CAN'T READ/ ik kan letterlijk kiezen welke exception ik wil hebben?? < nope.
            nf.printStackTrace();
        } catch (ChecksumException e) {

            //e.printStackTrace();
        }
        return null;
    }
}
