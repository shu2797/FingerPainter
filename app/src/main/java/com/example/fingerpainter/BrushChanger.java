package com.example.fingerpainter;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class BrushChanger extends AppCompatActivity {

    int width;
    Paint.Cap shape;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    SeekBar seekBar;
    TextView textView;
    FloatingActionButton FAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_changer);
        Bundle extras = getIntent().getExtras();
        shape = (Paint.Cap) extras.getSerializable("brush");
        width = extras.getInt("width");
        radioGroup = findViewById(R.id.radioGroup1);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView3);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        FAB = findViewById(R.id.floatingActionButton);
        textView.setTextColor(0xFF309700);

        if (shape == Paint.Cap.ROUND){
            radioGroup.check(R.id.radioButton1);
            textView.setText("\u2B24");
        } else {
            radioGroup.check(R.id.radioButton2);
            textView.setText("\u25A0");
        }


        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)width*2);
        seekBar.setProgress(width-10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((float)progress+10)*2);
                //textView.setText(Integer.toString(progress+1));
                //textView.setText(width*2);
                width = progress + 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1){
                    shape = Paint.Cap.ROUND;
                    textView.setText("\u2B24");
                } else {
                    shape = Paint.Cap.SQUARE;
                    textView.setText("\u25A0");
                }
            }
        });

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                Bundle extras = new Bundle();
                extras.putInt("width", width);

                extras.putSerializable("shape", shape);
                data.putExtras(extras);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
