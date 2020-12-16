package local.example.voleibol;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dadesPartit extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    EditText entradatext1, entradatext2, entradatext3, entradatext4;
    String cognom1, cognom2, cognom3, cognom4;
    int capitaA, capitaB;
   Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadespartit);
        bt1 = findViewById(R.id.dades_bt1);
        entradatext1 = findViewById(R.id.entradatext1);
        entradatext2 = findViewById(R.id.entradatext2);
        entradatext3 = findViewById(R.id.entradatext3);
        entradatext4 = findViewById(R.id.entradatext4);
        bt1.setOnClickListener(this);
        spinner(spinner);

    }

    @Override
    public void onClick(View v) {
        cognom1 = entradatext1.getText().toString();
        cognom2 = entradatext2.getText().toString();
        cognom3 = entradatext3.getText().toString();
        cognom4 = entradatext4.getText().toString();
        capitaA = capita(v);
        capitaB = capita(v);
        Intent temp;
        if (v.getId() == R.id.dades_bt1) {
            temp = new Intent(this, partit.class);
            startActivity(temp);
        } else {
        }
    }

    public void spinner(Spinner spinner){
        spinner = findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

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

}
