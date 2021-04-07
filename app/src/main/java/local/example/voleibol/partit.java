package local.example.voleibol;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class partit extends AppCompatActivity implements View.OnClickListener {
    Button bt1, bt2;
    parellaDatabase parellaDatabase1, parellaDatabase2;
    partitdatabase partitdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partit);
        parellaDatabase1 = (parellaDatabase) getIntent().getSerializableExtra("parella1");
        parellaDatabase2 = (parellaDatabase) getIntent().getSerializableExtra("parella2");
        partitdata = (partitdatabase) getIntent().getSerializableExtra("partitdata");
        /*bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v) {
    }
}