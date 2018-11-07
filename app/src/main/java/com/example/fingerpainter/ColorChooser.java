package com.example.fingerpainter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ColorChooser extends AppCompatActivity {

    int ncolor; //current colour
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_chooser);
        ncolor = getIntent().getExtras().getInt("tColor"); //receive current colour
        showColorChooser(); //display color chooser dialog
    }

    public void showColorChooser(){

        AmbilWarnaDialog colorChooser = new AmbilWarnaDialog(this, ncolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) { //clicking Cancel
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data); //send bad result code to cancel colour change
                finish();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) { //Clicking OK
                Intent data = new Intent();
                data.putExtra("newColor", color); //send new colour
                setResult(RESULT_OK, data); //approve result code and confirm color change
                finish();
            }
        });
        colorChooser.show();
    }

}
