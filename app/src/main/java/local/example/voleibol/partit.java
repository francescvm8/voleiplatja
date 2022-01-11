package local.example.voleibol;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class partit extends AppCompatActivity implements View.OnClickListener {
    Button btPuntsDreta, btPuntsEsquerra;
    ImageButton btDesfer;
    parellaDatabase parella1, parella2;
    partitdatabase partit;
    ImageView pilotaA1, pilotaA2, pilotaB1, pilotaB2;
    String[] numeros;
    TextView jugadorA1, jugadorA2, jugadorB1, jugadorB2;
    int punts1Cache = 0, punts2Cache = 0, sacadorActual1 = 0, sacadorActual2 = 0;
    int canvisDeCamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partit);
        Resources res = getResources();

        //obtenim els objectes parella1, parella2 i les dades del partit
        parella1 = (parellaDatabase) getIntent().getSerializableExtra("parella1");
        parella2 = (parellaDatabase) getIntent().getSerializableExtra("parella2");
        partit = (partitdatabase) getIntent().getSerializableExtra("partitdata");

        btPuntsDreta = findViewById(R.id.btPuntsDreta);
        btPuntsEsquerra = findViewById(R.id.btPuntsEsquerra);
        pilotaA1 = findViewById(R.id.imageView2);
        pilotaA2 = findViewById(R.id.imageView3);
        pilotaB1 = findViewById(R.id.imageView5);
        pilotaB2 = findViewById(R.id.imageView4);
        btDesfer = findViewById(R.id.btDesfer);
        numeros = res.getStringArray(R.array.numeros);
        jugadorA1 = findViewById(R.id.tv_jugadorA1_partit);
        jugadorA2 = findViewById(R.id.tv_jugadorA2_partit);
        jugadorB1 = findViewById(R.id.tv_jugadorB1_partit);
        jugadorB2 = findViewById(R.id.tv_jugadorB2_partit);

        btPuntsDreta.setOnClickListener(this);
        btPuntsEsquerra.setOnClickListener(this);
        btDesfer.setOnClickListener(this);

        jugadorAlSaqueInici(parella1, parella2, partit);
        colorSamarreta(parella1, parella2);
    }

    @Override
    public void onClick(View v) {
        int sacadorNum;
        String sacador = partit.getGuanyadorSorteig();
        if (sacador.equalsIgnoreCase("A")) {
            sacadorNum = 1;
        } else {
            sacadorNum = 2;
        }
        int equipUltimPunt = sacadorNum;


        //No funciona be el canvi de sacadors
        //Suma punts a les parelles en funcio de si estan a un costat del camp o a l'altre
        if (v.getId() == R.id.btPuntsDreta && canvisDeCamp % 2 == 0) {
            sumaPunts(btPuntsDreta, parella2);
            punts2Cache += 1;
            canviSacador(equipUltimPunt);
            equipUltimPunt = 2;

        } else if (v.getId() == R.id.btPuntsEsquerra && canvisDeCamp % 2 == 0) {
            sumaPunts(btPuntsEsquerra, parella1);
            punts1Cache += 1;
            canviSacador(equipUltimPunt);
            equipUltimPunt = 1;
        } else if (v.getId() == R.id.btPuntsDreta && canvisDeCamp % 2 == 1) {
            sumaPunts(btPuntsDreta, parella1);
            punts1Cache += 1;
            canviSacador(equipUltimPunt);
            equipUltimPunt = 1;
        } else if (v.getId() == R.id.btPuntsEsquerra && canvisDeCamp % 2 == 1) {
            sumaPunts(btPuntsEsquerra, parella2);
            punts2Cache += 1;
            canviSacador(equipUltimPunt);
            equipUltimPunt = 2;
        }
        if ((punts1Cache + punts2Cache) == 7) {
            canviDeCamp();
            canvisDeCamp += 1;
            punts1Cache = 0;
            punts2Cache = 0;
            jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);
        }
        if ((punts1Cache + punts2Cache) % 7 == 0) {
            canviDeCamp();
            canvisDeCamp += 1;
            punts1Cache = 0;
            punts2Cache = 0;
            jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);
        }
        jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);
    }

    private void sumaPunts(Button btPunts, parellaDatabase parella) {
        btPunts.setText(numeros[parella.getPunts() + 1]);
        parella.setPunts(parella.getPunts() + 1);
    }

    //Si l'ultim punt es de l'altre equip canvi de sacador
    private void canviSacador(int ultimPunt) {
        if (ultimPunt == 1) {
            sacadorActual2 += 1;
        }
        if (ultimPunt == 2) {
            sacadorActual1 += 1;
        }
        if (sacadorActual2 > 2) {
            sacadorActual2 = 1;
        } else if (sacadorActual1 > 2) {
            sacadorActual1 = 1;
        }
    }

    //Carrega el jugador inicial al saque al principi del partit
    @SuppressLint("WrongConstant")
    private void jugadorAlSaqueInici(parellaDatabase parella1, parellaDatabase parella2, partitdatabase partit) {
        String guanyaSorteig = partit.getGuanyadorSorteig();
        if (guanyaSorteig.equalsIgnoreCase("A")) {
            if (parella1.getSacador().equalsIgnoreCase("1")) {
                pilotaA1.setVisibility(1);
                sacadorActual1 = 1;
            } else if (parella1.getSacador().equalsIgnoreCase("2")) {
                pilotaA2.setVisibility(1);
                sacadorActual1 = 2;
            }
        } else if (guanyaSorteig.equalsIgnoreCase("B")) {
            if (parella2.getSacador().equalsIgnoreCase("1")) {
                pilotaB1.setVisibility(1);
                sacadorActual2 = 1;
            } else if (parella2.getSacador().equalsIgnoreCase("2")) {
                pilotaB2.setVisibility(1);
                sacadorActual2 = 2;
            }
        }
    }

    //Carrega els colors de samarreta de cada equip en el background color del textView de cada jugador
    private void colorSamarreta(parellaDatabase parella1, parellaDatabase parella2) {
        String colorParella1 = parella1.getTeamColor();
        String colorParella2 = parella2.getTeamColor();

        int myColorA = Color.parseColor(colorParella1);
        int myColorB = Color.parseColor(colorParella2);
        jugadorA1.setBackgroundColor(myColorA);
        jugadorA2.setBackgroundColor(myColorA);
        jugadorB1.setBackgroundColor(myColorB);
        jugadorB2.setBackgroundColor(myColorB);
    }

    //Cada cop que es crida el metode posa visible el simbol de la pilota per al jugador al saque
    @SuppressLint("WrongConstant")
    private void jugadorAlSaque(int equip, int sacadorActual1, int sacadorActual2) {
        if (equip == 1 && sacadorActual1 == 1) {
            pilotaA1.setVisibility(View.VISIBLE);
            pilotaA2.setVisibility(View.INVISIBLE);
            pilotaB1.setVisibility(View.INVISIBLE);
            pilotaB2.setVisibility(View.INVISIBLE);
        }
        if (equip == 1 && sacadorActual1 == 2) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.VISIBLE);
            pilotaB1.setVisibility(View.INVISIBLE);
            pilotaB2.setVisibility(View.INVISIBLE);
        }
        if (equip == 2 && sacadorActual2 == 1) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.INVISIBLE);
            pilotaB1.setVisibility(View.VISIBLE);
            pilotaB2.setVisibility(View.INVISIBLE);
        }
        if (equip == 2 && sacadorActual2 == 2) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.INVISIBLE);
            pilotaB1.setVisibility(View.INVISIBLE);
            pilotaB2.setVisibility(View.VISIBLE);
        }
    }

    //Canvia els equips de camp i mostra un toast avisant-ho
    private void canviDeCamp() {
        String puntsDretaCache = btPuntsDreta.getText().toString();
        btPuntsDreta.setText(btPuntsEsquerra.getText().toString());
        btPuntsEsquerra.setText(puntsDretaCache);

        if (canvisDeCamp % 2 == 0) {
            colorSamarreta(parella1, parella2);
        } else if (canvisDeCamp % 2 == 1) {
            colorSamarreta(parella2, parella1);
        }
        Toast.makeText(this, this.getString(R.string.canviDeCamp), Toast.LENGTH_SHORT).show();
    }

}