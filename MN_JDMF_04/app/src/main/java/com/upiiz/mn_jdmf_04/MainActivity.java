package com.upiiz.mn_jdmf_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.mn_jdmf_04.util.Basicas;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNum1;
    TextView tvNum2;
    TextView tvResult;
    EditText etNum1;
    EditText etNum2;
    Button btnSum;
    Button btnRest;
    Button btnDivi;
    Button btnMulti;

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

        tvNum1 = findViewById(R.id.tvNum1);
        tvNum2 = findViewById(R.id.tvNum2);
        tvResult = findViewById(R.id.tvResult);
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnDivi = findViewById(R.id.btnDivi);
        btnMulti = findViewById(R.id.btnMulti);
        btnRest = findViewById(R.id.btnResta);
        btnSum = findViewById(R.id.btnSuma);

        btnSum.setOnClickListener(this);
        btnRest.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivi.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String num1Tex = etNum1.getText().toString();
        String num2Tex = etNum2.getText().toString();

        if (num1Tex.isEmpty() || num2Tex.isEmpty()){
            Toast.makeText(this, "Por favor ingrese ambos números", Toast.LENGTH_SHORT).show();
            return;
        }

        float n1 = Float.parseFloat(num1Tex);
        float n2 = Float.parseFloat(num2Tex);

        int id = v.getId();
        float result = 0;

        if (id == btnSum.getId()){
            result = Basicas.sumar(n1,n2);
        }else if (id == btnRest.getId()) {
            result = Basicas.restar(n1, n2);
        } else if (id == btnMulti.getId()) {
            result = Basicas.multiplicar(n1, n2);
        } else if (id == btnDivi.getId()) {
            if (n2 == 0) {
                Toast.makeText(this, "No es posible dividir entre cero", Toast.LENGTH_SHORT).show();
                return;
            }
            result = Basicas.dividir(n1, n2);
        }
        tvResult.setText(String.valueOf(result));

    }
}