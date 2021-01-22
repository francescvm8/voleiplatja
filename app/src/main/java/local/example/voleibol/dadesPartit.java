package local.example.voleibol;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class dadesPartit extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
    EditText entradatext1, entradatext2, entradatext3, entradatext4;
    ArrayList<String> colors = new ArrayList<>();
    String colorA, colorB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadespartit);
        bt1 = findViewById(R.id.dades_bt1);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);
        rb7 = findViewById(R.id.rb7);
        entradatext1 = findViewById(R.id.entradatext1);
        entradatext2 = findViewById(R.id.entradatext2);
        entradatext3 = findViewById(R.id.entradatext3);
        entradatext4 = findViewById(R.id.entradatext4);
        bt1.setOnClickListener(this);
        //colorpicker team B
        final FloatingActionButton fab2 = findViewById(R.id.fab);
        //colorpicker team A
        final FloatingActionButton fab = findViewById(R.id.fab2);
        //Color picker code
        if (fab != null || fab2 != null) {
            fab.setOnClickListener(view -> {
                final ColorPicker colorPicker = new ColorPicker(dadesPartit.this);
                //verd
                colors.add("#33cc33");
                //blau
                colors.add("#33ccff");
                //groc
                colors.add("#ffff00");
                //taronja
                colors.add("#ff9933");
                //blanc
                colors.add("#ffffff");
                //negre
                colors.add("#000000");
                //rosa
                colors.add("#ff00ff");
                //vermell
                colors.add("#ff0000");


                colorPicker
                        .setDefaultColorButton(Color.parseColor("#f84c44"))
                        .setColors(colors)
                        .setColumns(5)
                        .setRoundColorButton(true)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                Log.d("position", "" + position);// will be fired only when OK button was tapped
                                colorA = colors.get(position);
                                fab.setBackgroundTintList(ColorStateList.valueOf(color));
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).show();
            });
            fab2.setOnClickListener(view -> {
                final ColorPicker colorPicker = new ColorPicker(dadesPartit.this);

                colorPicker
                        .setDefaultColorButton(Color.parseColor("#f84c44"))
                        .setColors(colors)
                        .setColumns(5)
                        .setRoundColorButton(true)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                Log.d("position", "" + position);// will be fired only when OK button was tapped
                                colorB = colors.get(position);
                                fab2.setBackgroundTintList(ColorStateList.valueOf(color));
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).show();
            });
        }
    }

    @Override
    public void onClick(View v) {
        Intent temp;
        boolean a = name_required(entradatext1), b = name_required(entradatext2), c = name_required(entradatext3),
                d = name_required(entradatext4), e = capita_required(rb1, rb2),
                f = capita_required(rb3, rb4), g = genere_required(rb5, rb6, rb7);
        if (v.getId() == R.id.dades_bt1 && a && b && c && d && e && f && g) {
            parella parella1 = new parella();
            parella1.setCognom1(entradatext1.getText().toString());
            parella1.setCognom2(entradatext2.getText().toString());
            parella1.setCapita(capita(v));
            parella1.setTeamColor(colorA);
            parella parella2 = new parella();
            parella2.setCognom1(entradatext3.getText().toString());
            parella2.setCognom2(entradatext4.getText().toString());
            parella2.setCapita(capita(v));
            parella2.setTeamColor(colorB);
            partitdatabase partitdata = new partitdatabase();
            partitdata.setParella1(parella1);
            partitdata.setParella2(parella2);
            partitdata.setGenere(genere(v));

            temp = new Intent(this, partit.class);
            startActivity(temp);
        }
    }

    @SuppressLint("NonConstantResourceId")
    public int capita(View v) {
        switch (v.getId()) {
            case R.id.rb1:
            case R.id.rb3:
                return 1;
            case R.id.rb2:
            case R.id.rb4:
                return 2;
        }
        return 0;
    }

    public boolean capita_required(RadioButton rb1, RadioButton rb2) {
        if (rb1.isChecked() || rb2.isChecked()) {
            return true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.captian_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public String genere(View v) {
        switch (v.getId()) {
            case R.id.rb5:
                return "Mixte";
            case R.id.rb6:
                return "Masculi";
            case R.id.rb7:
                return "Femeni";
        }
        return "null";
    }

    public boolean genere_required(RadioButton rb1, RadioButton rb2, RadioButton rb3) {
        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
            return true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.genere_required),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean name_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("First name is required!");
            return false;
        }
        return true;
    }
}
