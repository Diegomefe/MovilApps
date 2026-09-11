package com.upiiz.dm_jdmf_03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMetros;
    EditText etPies;
    Button btnMetros;
    Button btnPies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMetros = findViewById(R.id.etMetros);
        etPies = findViewById(R.id.etPies);
        btnMetros = findViewById(R.id.btnMetros);
        btnPies = findViewById(R.id.btnPies);

        btnMetros.setOnClickListener(this);
        btnPies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnMetros.getId()){
            String piesTex = etPies.getText().toString();
            if (!piesTex.isEmpty()){
                float pies = Float.parseFloat(piesTex);
                float metros = (float) (pies/3.281);
                etMetros.setText(String.format(Locale.US, "%.2f",metros));
            } else{
                etMetros.setError("Ingresa un valor");
            }

        }
        if (id == btnPies.getId()){
            String metrosTex = etMetros.getText().toString();
            if (!metrosTex.isEmpty()){
                float metros = Float.parseFloat(metrosTex);
                float pies = (float) (metros*3.281);
                etPies.setText(String.format(Locale.US,"%.2f",pies));
            } else{
                etPies.setError("Ingresa un valor");
            }
        }
    }
}