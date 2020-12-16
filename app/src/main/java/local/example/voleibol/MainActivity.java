package local.example.voleibol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent temp;
        if (v.getId() == R.id.bt1) {
            temp = new Intent(this, dadesPartit.class);
            startActivity(temp);
        } else if (v.getId() == R.id.bt2) {
            temp = new Intent(this, partitAnterior.class);
            startActivity(temp);
        }  else {
        }
    }
}