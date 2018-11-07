package com.example.fingerpainter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import java.io.File;

import static com.example.fingerpainter.R.id.myFingerPainterView;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton brushFAB, colorFAB, saveFAB, clearFAB, shareFAB;

    public FingerPainterView myFPV; //instantiating the view

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFPV = findViewById(myFingerPainterView);

        //Floating Action Buttons
        brushFAB = findViewById(R.id.brushFAB);
        colorFAB = findViewById(R.id.colorFAB);
        saveFAB = findViewById(R.id.saveFAB);
        clearFAB = findViewById(R.id.clearFAB);
        shareFAB = findViewById(R.id.shareFAB);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); //request permission to write to internal storage

        myFPV.load(getIntent().getData()); //load image

        colorFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ColorChooser.class);
                int thisColor = myFPV.getColour(); //get current colour
                i.putExtra("tColor", thisColor); //send colour to second activity Color Chooser
                startActivityForResult(i, 0);
            }
        });

        brushFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), BrushChanger.class);
                Paint.Cap p = myFPV.getBrush();
                int width = myFPV.getBrushWidth();
                Bundle extras = new Bundle();
                extras.putInt("width", width);
                extras.putSerializable("brush", p);
                i.putExtras(extras);
                startActivityForResult(i, 2);
            }
        });

        saveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFPV.save();
            }
        });

        clearFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this); //create dialog confirming that user wants to clear all

                alert
                        .setTitle("Clear all")
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myFPV.clear();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        shareFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File image = myFPV.save(); //save file before sharing


                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(image.getAbsolutePath()));
                startActivity(Intent.createChooser(shareIntent, "Share File Using"));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //from color changer
        if ((requestCode == 0) &&
                (resultCode == RESULT_OK)) {
            int newColor = data.getExtras().getInt("newColor"); //receive new colour from Color Chooser
            myFPV.setColour(newColor); //set new colour
        }

        //from brush changer
        if ((requestCode == 2) && (resultCode == RESULT_OK)){
            Bundle extras = data.getExtras();
            myFPV.setBrushWidth(extras.getInt("width"));
            myFPV.setBrush((Paint.Cap)extras.getSerializable("shape"));
        }
    }
}
