package local.example.voleibol;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class dadesPartit extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    String cognom1, cognom2, cognom3, cognom4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadespartit);
        bt1 = findViewById(R.id.dades_bt1);
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cognom1 = String.valueOf(.getText().toString());
        Intent temp;
        if (v.getId() == R.id.dades_bt1) {
            temp = new Intent(this, partit.class);
            startActivity(temp);
        }  else {
        }
    }
}
