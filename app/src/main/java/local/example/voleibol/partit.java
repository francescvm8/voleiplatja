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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class partit extends AppCompatActivity implements View.OnClickListener, ValueEventListener, ChildEventListener {
    Button btPuntsDreta, btPuntsEsquerra;
    ImageButton btDesfer;
    parellaDatabase parella1, parella2;
    partitDatabase partit;
    DatabaseReference dbPartit, dbPartit2;
    ImageView pilotaA1, pilotaA2, pilotaB1, pilotaB2;
    String[] numeros;
    TextView jugadorA1, jugadorA2, jugadorB1, jugadorB2;
    int equipUltimPunt = 0, punts1 = 0, punts2 = 0, punts1Cache = 0, punts2Cache = 0, sacadorActual1 = 0,
            sacadorActual2 = 0, parella1setsGuanyats = 0, parella2setsGuanyats = 0, canvisDeCamp = 0, setActual = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partit);
        Resources res = getResources();

        //obtenim els objectes parella1, parella2 i les dades del partit
        parella1 = (parellaDatabase) getIntent().getSerializableExtra("parella1");
        parella2 = (parellaDatabase) getIntent().getSerializableExtra("parella2");
        partit = (partitDatabase) getIntent().getSerializableExtra("partitData");

        String nomParella = parella1.getCognom1() + " " + parella1.getCognom2() + " - " + parella2.getCognom1() + " " + parella2.getCognom2();

        dbPartit = FirebaseDatabase.getInstance().getReference().child("partit").child(partit.getLloc()).child(partit.getSexeCategoria()).child(nomParella);
        dbPartit.addChildEventListener(this);
        dbPartit.addValueEventListener(this);

        dbPartit2 = FirebaseDatabase.getInstance().getReference().child("partit").child(partit.getLloc()).child(partit.getSexeCategoria()).child(nomParella);
        dbPartit2.addChildEventListener(this);
        dbPartit2.addValueEventListener(this);

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

        jugadorAlSaqueInici(parella1, parella2);
        colorSamarreta(parella1, parella2);
        String sacador = partit.getEquipIniciSaque();
        if (sacador.equalsIgnoreCase("A")) {
            equipUltimPunt = 1;
        } else {
            equipUltimPunt = 2;
        }
        jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);
        if (equipUltimPunt == 1) sacadorActual2 -= 1;
        if (equipUltimPunt == 2) sacadorActual1 -= 1;
    }

    @Override
    public void onClick(View v) {
        //No funciona be el canvi de l'icona dels sacadors
        //Suma punts a les parelles en funcio de si estan a un costat del camp o a l'altre
        if (v.getId() == R.id.btPuntsEsquerra && canvisDeCamp % 2 == 0) {
            sumaPunts(btPuntsEsquerra, punts1);
            punts1Cache += 1;
            punts1 += 1;
            if (equipUltimPunt == 2) {
                sacadorActual1 = canviSacador(sacadorActual1);
            }
            equipUltimPunt = 1;
        }
        if (v.getId() == R.id.btPuntsDreta && canvisDeCamp % 2 == 0) {
            sumaPunts(btPuntsDreta, punts2);
            punts2Cache += 1;
            punts2 += 1;
            if (equipUltimPunt == 1) {
                sacadorActual2 = canviSacador(sacadorActual2);
            }
            equipUltimPunt = 2;
        }
        //Aquest if es per quan han canviat de camp
        if (v.getId() == R.id.btPuntsDreta && canvisDeCamp % 2 == 1) {
            sumaPunts(btPuntsDreta, punts1);
            punts1Cache += 1;
            punts1 += 1;
            if (equipUltimPunt == 1) {
                sacadorActual2 = canviSacador(sacadorActual2);
            }
            equipUltimPunt = 1;
        }
        //Aquest if es per quan han canviat de camp
        if (v.getId() == R.id.btPuntsEsquerra && canvisDeCamp % 2 == 1) {
            sumaPunts(btPuntsEsquerra, punts2);
            punts2Cache += 1;
            punts2 += 1;
            if (equipUltimPunt == 2) {
                sacadorActual1 = canviSacador(sacadorActual1);
            }
            equipUltimPunt = 2;
        }

        if ((punts1Cache + punts2Cache) % 7 == 0) {
            canviDeCamp();
            canvisDeCamp += 1;
            punts1Cache = 0;
            punts2Cache = 0;
            int sacadorTemp = sacadorActual2;
            sacadorActual2 = sacadorActual1;
            sacadorActual1 = sacadorTemp;
        }

        //El icono de jugador al saque encara no funciona be, el numero del jugador si
        if (canvisDeCamp % 2 == 0) jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);

        //Invertim l'ordre del equipUltimPunt ja que han canviat el camp
        if (canvisDeCamp % 2 == 1) {
            equipUltimPunt += 1;
            if (equipUltimPunt > 2) {
                equipUltimPunt = 1;
            }
            //En el cas de 7-0 pel a l'equip A posa el sacadorActual2 correctament
            if (punts2 == 0 & canvisDeCamp == 1 & punts1 + punts2 == 7) {
                sacadorActual2 += 1;
            }
            jugadorAlSaque(equipUltimPunt, sacadorActual1, sacadorActual2);
        }
        if (punts1 >= 21 || punts2 >= 21) {
            int diferenciaPunts1 = punts1 - punts2;
            int diferenciaPunts2 = punts2 - punts1;
            if (diferenciaPunts1 >= 2) {
                finalDelSet("parella1");
            } else if (diferenciaPunts2 >= 2) {
                finalDelSet("parella2");
            }
        }
    }

    private void sumaPunts(Button btPunts, int punts) {
        btPunts.setText(numeros[punts + 1]);
    }

    //Si l'ultim punt es de l'altre equip canvi de sacador
    private int canviSacador(int sacadorActual) {
        sacadorActual += 1;
        if (sacadorActual > 2) {
            sacadorActual = 1;
        }
        return sacadorActual;
    }

    //Carrega el jugador inicial al saque al principi del partit
    @SuppressLint("WrongConstant")
    private void jugadorAlSaqueInici(parellaDatabase parella1, parellaDatabase parella2) {
        if (parella1.getSacador().equalsIgnoreCase("1")) {
            sacadorActual1 = 1;
        } else if (parella1.getSacador().equalsIgnoreCase("2")) {
            sacadorActual1 = 2;
        }
        if (parella2.getSacador().equalsIgnoreCase("1")) {
            sacadorActual2 = 1;
        } else if (parella2.getSacador().equalsIgnoreCase("2")) {
            sacadorActual2 = 2;
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
        } else if (equip == 1 && sacadorActual1 == 2) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.VISIBLE);
            pilotaB1.setVisibility(View.INVISIBLE);
            pilotaB2.setVisibility(View.INVISIBLE);
        } else if (equip == 2 && sacadorActual2 == 1) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.INVISIBLE);
            pilotaB1.setVisibility(View.VISIBLE);
            pilotaB2.setVisibility(View.INVISIBLE);
        } else if (equip == 2 && sacadorActual2 == 2) {
            pilotaA1.setVisibility(View.INVISIBLE);
            pilotaA2.setVisibility(View.INVISIBLE);
            pilotaB1.setVisibility(View.INVISIBLE);
            pilotaB2.setVisibility(View.VISIBLE);
        }
    }

    //Canvia els equips de camp i mostra un toast avisant-ho
    private void canviDeCamp() {
        //Restem un als punts ja que al fer el suma punts sumara un punt a cada equip
        int puntsDreta = punts1 - 1, puntsEsquerra = punts2 - 1;
        //Si estaven al camp contrari al que han començat s'inverteix els punts
        if (canvisDeCamp % 2 == 1) {
            puntsDreta = punts2 - 1;
            puntsEsquerra = punts1 - 1;
        }
        sumaPunts(btPuntsEsquerra, puntsEsquerra);
        sumaPunts(btPuntsDreta, puntsDreta);

        if (canvisDeCamp % 2 == 0) {
            colorSamarreta(parella2, parella1);
        } else if (canvisDeCamp % 2 == 1) {
            colorSamarreta(parella1, parella2);
        }
        Toast.makeText(this, this.getString(R.string.canviDeCamp), Toast.LENGTH_SHORT).show();
    }

    //Puja a la base de dades el resultat del set, l'equip guanyador i reinicia el comptador
    private void finalDelSet(String parellaGuanyadora) {
        if (parellaGuanyadora.equalsIgnoreCase("parella1")) {
            if (setActual == 1) {
                partit.setSet1("Parella 1");
            } else if (setActual == 2) {
                partit.setSet2("Parella 1");
            } else if (setActual == 3) {
                partit.setSet3("Parella 1");
            }
            parella1setsGuanyats++;
        } else if (parellaGuanyadora.equalsIgnoreCase("parella2")) {
            if (setActual == 1) {
                partit.setSet1("Parella 2");
            } else if (setActual == 2) {
                partit.setSet2("Parella 2");
            } else if (setActual == 3) {
                partit.setSet3("Parella 2");
            }
            parella2setsGuanyats++;
        }
        setPuntsDatabase();
        setActual++;

        punts1 = 0;
        punts2 = 0;
        punts1Cache = 0;
        punts2Cache = 0;

        sumaPunts(btPuntsEsquerra, -1);
        sumaPunts(btPuntsEsquerra, -1);

        Toast.makeText(this, this.getString(R.string.finalDeSet), Toast.LENGTH_SHORT).show();
    }

    //Puja els punts a la base de dades en funcio del set en el que estan
    private void setPuntsDatabase() {
        dbPartit.orderByChild("parelladatabase1").equalTo("parelladatabase1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String key = datas.getKey();
                        //String name=datas.child("name").getValue().toString();
                        //String priorities=datas.child("priority").getValue().toString();
                        dbPartit.child(key).child("codiParella").setValue(parella1.getCodiParella());
                        dbPartit.child(key).child("cognom1").setValue(parella1.getCognom1());
                        dbPartit.child(key).child("cognom2").setValue(parella1.getCognom2());
                        dbPartit.child(key).child("capita").setValue(parella1.getCapita());
                        dbPartit.child(key).child("teamColor").setValue(parella1.getTeamColor());
                        dbPartit.child(key).child("sacador").setValue(parella1.getSacador());
                        if (setActual == 1) {
                            dbPartit.child(key).child("puntsSet1").setValue(punts1);
                        }
                        if (setActual == 2) {
                            dbPartit.child(key).child("puntsSet2").setValue(punts1);
                        }
                        if (setActual == 3) {
                            dbPartit.child(key).child("puntsSet3").setValue(punts1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        dbPartit.orderByChild("parelladatabase2").equalTo("parelladatabase2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String key = datas.getKey();
                        //String name=datas.child("name").getValue().toString();
                        //String priorities=datas.child("priority").getValue().toString();
                        dbPartit.child(key).child("codiParella").setValue(parella2.getCodiParella());
                        dbPartit.child(key).child("cognom1").setValue(parella2.getCognom1());
                        dbPartit.child(key).child("cognom2").setValue(parella2.getCognom2());
                        dbPartit.child(key).child("capita").setValue(parella2.getCapita());
                        dbPartit.child(key).child("teamColor").setValue(parella2.getTeamColor());
                        dbPartit.child(key).child("sacador").setValue(parella2.getSacador());
                        if (setActual == 1) {
                            dbPartit.child(key).child("puntsSet1").setValue(punts2);
                        }
                        if (setActual == 2) {
                            dbPartit.child(key).child("puntsSet2").setValue(punts2);
                        }
                        if (setActual == 3) {
                            dbPartit.child(key).child("puntsSet3").setValue(punts2);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });
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