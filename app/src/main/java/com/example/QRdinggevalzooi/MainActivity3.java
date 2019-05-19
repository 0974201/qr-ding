package com.example.QRdinggevalzooi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.QRdinggevalzooi.ui.mainactivity3.MainActivity3Fragment;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity3_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainActivity3Fragment.newInstance())
                    .commitNow();
        }
    }
}
