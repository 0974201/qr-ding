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
    //aaagh het is een tering bende, ik fix alles later wel wanneer ik kan lezen. ┐('～`;)┌

    public Result decodeZzz(BinaryBitmap plaatje) throws NotFoundException, FormatException {
        //ok hij gaat een image opeten, geen idee wat ik met de exceptions ga doen, moet ik later uitzoeken.
        //???nfc je kan ook nfc zooi doen??
        QRCodeReader qrR = new QRCodeReader(); // (*・ω・)ﾉ
        String qrResultDing; //string aanmaken waarin de qr zooi ingepropt kan worden (aangezien het een string teruggeeft)

        HashMap<DecodeHintType,Object> decodeDing = new HashMap<>(); //qrDing had een hashmap dat encodeDing heette dus vanaf nu heet jij decodeDing :D
        //in decodeDing kan je shit proppen dat de decoder helpt tijdens het uitlezen van een qrcode, including telling it to try harder when it can't find shit
        //enige reden waarom ik het heb meegenomen uit de documentatie lel. (wedden dat als ik mijn medicatie heb genomen en wat meer wakker ben ik dit ga weghalen omdat het te moeilijk is idek)
        decodeDing.put(DecodeHintType.TRY_HARDER,Boolean.TRUE); //uh, moet later documentatie doornemen om te kijken wat het precies doet, for now i'm just adding the boolean bc it told me to do so
        //iig, het gaat meer tijd besteden aan het zoeken naar een qr code maar gaat wel ten koste van de snelheid.
        //i can't read anymore

        try{
            Result res = qrR.decode(plaatje); //iig, hij stopt het plaatje in dit ding. and then magic happens (/￣ー￣)/~~☆’.･.･:★’.･.･:☆
            qrResultDing = res.getText(); //(waarom wou ik dit omgekeerd doen?) en hier wordt res uitgelezen en in de string gepropt wat ik eerder had gemaakt
            //en dan kan je leuke dingen mee doen.
            //return qrResultDing; //< sssh ik fix het later wel als ik minder slaperig ben en shit kan lezen.
            return res; //DAN MAAR ZO ZODAT IE FF STOPT MET ZEIKEN ( ` ω ´ ). aaaaaaa. ( ╥ω╥ )
        } catch (NotFoundException nf){
            //aaaaaaaah oke wat doe ik met de rest. NVM I CAN'T READ/ ik kan letterlijk kiezen welke exception ik wil hebben??
            nf.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        }
        return null;
    }
}
