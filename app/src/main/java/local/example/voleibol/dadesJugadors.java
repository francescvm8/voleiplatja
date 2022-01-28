package local.example.voleibol;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class dadesJugadors extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, ChildEventListener, ValueEventListener {
    Button bt1, bt2;
    EditText entradatext1, entradatext2, entradatext3, entradatext4;
    TextView tv5, tv6, tv7;
    ArrayList<String> colors = new ArrayList<>();
    int numero;
    String colorA = "33ccff", colorB = "33ccff", lloc, arbitre1, arbitre2, anotador, horaInici, pista;
    Spinner spinner, spinner_capitaA, spinner_capitaB, spinner_saqueA, spinner_saqueB, spinner_guanyadorSorteig, spinner_saqueIniciEquip;
    parellaDatabase parella1, parella2;
    partitDatabase partitdata;
    DatabaseReference dbPartit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadesjugadors);

        dbPartit = FirebaseDatabase.getInstance().getReference().child("partit");
        dbPartit.addChildEventListener(this);
        dbPartit.addValueEventListener(this);

        lloc = (String) getIntent().getSerializableExtra("lloc");
        arbitre1 = (String) getIntent().getSerializableExtra("arbitre1");
        arbitre2 = (String) getIntent().getSerializableExtra("arbitre2");
        anotador = (String) getIntent().getSerializableExtra("anotador");
        horaInici = (String) getIntent().getSerializableExtra("hora");
        pista = (String) getIntent().getSerializableExtra("pista");

        bt1 = findViewById(R.id.dades_bt1);
        bt2 = findViewById(R.id.dades_bt2);
        bt1.setVisibility(Button.INVISIBLE);
        entradatext1 = findViewById(R.id.text12);
        entradatext2 = findViewById(R.id.entradatext2);
        entradatext3 = findViewById(R.id.entradatext3);
        entradatext4 = findViewById(R.id.entradatext4);
        spinner = findViewById(R.id.spinner1);
        spinner_saqueIniciEquip = findViewById(R.id.spinner7);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        carregarColorPicker();
        carregarSpinners();
    }

    @Override
    public void onClick(View v) {
        Intent temp;
        Boolean a = false, b = false, c = false, d = false;

        //Quan han posat els noms i clica al boto del sorteig es realitza el sorteig
        if (v.getId() == R.id.dades_bt2) {
            sorteig(v);
        }
        //Realitza els required dels noms
        if (v.getId() == R.id.dades_bt1) {
            a = name_required(entradatext1);
            b = name_required(entradatext2);
            c = name_required(entradatext3);
            d = name_required(entradatext4);
        }

        //Amb el sorteig fet es creen els objectes i es pujen al firebase
        if (v.getId() == R.id.dades_bt1 && a && b && c && d) {
            parella1 = new parellaDatabase("1", entradatext1.getText().toString(), entradatext2.getText().toString(), spinner_capitaA.getSelectedItem().toString(), colorA, spinner_saqueA.getSelectedItem().toString());
            parella2 = new parellaDatabase("2", entradatext3.getText().toString(), entradatext4.getText().toString(), spinner_capitaB.getSelectedItem().toString(), colorB, spinner_saqueB.getSelectedItem().toString());
            partitdata = new partitDatabase(parella1, parella2, horaInici, arbitre1, arbitre2, anotador, lloc, pista, spinner_guanyadorSorteig.getSelectedItem().toString(), spinner_saqueIniciEquip.getSelectedItem().toString(), spinner.getSelectedItem().toString());
            String nomPartit = lloc + "/" + spinner.getSelectedItem().toString() + "/" + parella1.getCognom1() + " " + parella1.getCognom2() + " - " + parella2.getCognom1() + " " + parella2.getCognom2();
            dbPartit.child(nomPartit).setValue(partitdata);

            temp = new Intent(this, partit.class);
            temp.putExtra("parella1", parella1);
            temp.putExtra("parella2", parella2);
            temp.putExtra("partitData", partitdata);
            startActivity(temp);
        }
    }

    //realitza el sorteig i activa el boto de continuar
    public void sorteig(View v) {
        if (v.getId() == R.id.dades_bt2) {
            numero = (int) (Math.random() * 2);
            switch (numero) {
                //cara
                case 0:
                    tv5.setText(R.string.cara);
                    tv5.setVisibility(TextView.VISIBLE);
                    break;
                //creu
                case 1:
                    tv5.setText(R.string.creu);
                    tv5.setVisibility(TextView.VISIBLE);
                    break;
            }
            bt1.setVisibility(Button.VISIBLE);
        }
    }

    //comprova que hi ha el nom i sino ho avisa
    public boolean name_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("First name is required!");
            return false;
        } else return true;
    }

    //Codi del color picker per als 2 equips
    public void carregarColorPicker() {
        //colorpicker team B
        final FloatingActionButton fab2 = findViewById(R.id.fab);
        //colorpicker team A
        final FloatingActionButton fab = findViewById(R.id.fab2);
        colors.clear();
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
        //rosa
        colors.add("#ff00ff");
        //vermell
        colors.add("#ff0000");
        //Color picker code
        if (fab != null || fab2 != null) {
            fab.setOnClickListener(view -> {
                final ColorPicker colorPicker = new ColorPicker(dadesJugadors.this);

                colorPicker
                        .setDefaultColorButton(Color.parseColor("#33ccff"))
                        .setColors(colors)
                        .setColumns(5)
                        .setRoundColorButton(true)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                Log.d("position", "" + position);// will be fired only when OK button was tapped
                                colorA = colors.get(position);
                                if (fab.getBackgroundTintList() != ColorStateList.valueOf(color)) {
                                    fab.setBackgroundTintList(ColorStateList.valueOf(color));
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).show();
            });
            fab2.setOnClickListener(view -> {
                final ColorPicker colorPicker = new ColorPicker(dadesJugadors.this);

                colorPicker
                        .setDefaultColorButton(Color.parseColor("#33ccff"))
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

    //Codi per carregar tots els spinners
    public void carregarSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

        spinner_capitaA = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.capita, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_capitaA.setAdapter(adapter1);
        spinner_capitaA.setSelection(0);
        spinner_capitaA.setOnItemSelectedListener(this);

        spinner_capitaB = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.capita, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_capitaB.setAdapter(adapter2);
        spinner_capitaB.setSelection(0);
        spinner_capitaB.setOnItemSelectedListener(this);

        spinner_saqueA = findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.capita, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_saqueA.setAdapter(adapter4);
        spinner_saqueA.setSelection(0);
        spinner_saqueA.setOnItemSelectedListener(this);

        spinner_saqueB = findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.capita, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_saqueB.setAdapter(adapter5);
        spinner_saqueB.setSelection(0);
        spinner_saqueB.setOnItemSelectedListener(this);

        spinner_guanyadorSorteig = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.a_b, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_guanyadorSorteig.setAdapter(adapter6);
        spinner_guanyadorSorteig.setSelection(0);
        spinner_guanyadorSorteig.setOnItemSelectedListener(this);

        spinner_saqueIniciEquip = findViewById(R.id.spinner7);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this,
                R.array.a_b, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_saqueIniciEquip.setAdapter(adapter7);
        spinner_saqueIniciEquip.setSelection(0);
        spinner_saqueIniciEquip.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
