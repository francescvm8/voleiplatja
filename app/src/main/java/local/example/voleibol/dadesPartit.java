package local.example.voleibol;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;

public class dadesPartit extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    EditText editText1, editText2, editText3, editText4, time, editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadespartit);

        bt1 = findViewById(R.id.dades_bt3);
        editText1 = findViewById(R.id.text11);
        editText2 = findViewById(R.id.text12);
        editText3 = findViewById(R.id.text13);
        editText4 = findViewById(R.id.text14);
        editText5 = findViewById(R.id.text5);
        bt1.setOnClickListener(this);
        time = findViewById(R.id.time);
        time.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(dadesPartit.this,
                    (timePicker, selectedHour, selectedMinute) -> time.setText(selectedHour + ":" + selectedMinute)
                    , hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });
    }

    @Override
    public void onClick(View v) {
        Intent temp;
        Boolean a = false, b = false, c = false, d = false;
        if (v.getId() == R.id.dades_bt3) {
            a = name_required(editText1);
            b = time_required(time);
            c = courtName_required(editText4);
            d = courtNumber_required(editText5);
        }
        String lloc = editText4.getText().toString();
        String arbitre1 = editText1.getText().toString();
        String arbitre2 = textArbitre(editText2);
        String anotador = textArbitre(editText3);
        if (a && b && c && d) {
            temp = new Intent(this, dadesJugadors.class);
            temp.putExtra("arbitre1", arbitre1);
            temp.putExtra("arbitre2", arbitre2);
            temp.putExtra("anotador", anotador);
            temp.putExtra("hora", time.getText().toString());
            temp.putExtra("lloc", lloc);
            temp.putExtra("pista", editText5.getText().toString());
            startActivity(temp);
        }
    }

    //retorna el text del segon i l'anotador, si no n'hi hagues hi passariem la string buida ""
    public String textArbitre(EditText text1) {
        if (text1.getText().toString().length() == 0) {
            return "";
        } else {
            return text1.getText().toString();
        }
    }

    //Especifica que el nom del primer àrbitre es necessari
    public boolean name_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("Referee name is required!");
            return false;
        } else return true;
    }

    //Espeficia que el camp de joc es necessari
    public boolean courtName_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("Court name is required!");
            return false;
        } else return true;
    }

    //Espeficia que el camp de joc es necessari
    public boolean courtNumber_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("Court number is required!");
            return false;
        } else return true;
    }

    //Espeficia que l'hora de joc es necessari
    public boolean time_required(EditText entradatext1) {
        if (entradatext1.getText().toString().length() == 0) {
            entradatext1.setError("Time is required!");
            return false;
        } else return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
