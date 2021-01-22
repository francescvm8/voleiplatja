package local.example.voleibol;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class partit extends AppCompatActivity implements View.OnClickListener {
    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partit);
        /*bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
    }}