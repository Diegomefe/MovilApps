package com.upiiz.dm_jdmf_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvDisplay;
    Button btnUno, btnDos, btnTres, btnVCuatro, btnCinco, btnSeis, btnSiete, btnOcho, btnNueve, btnCero, btnResiduo, btnIgual, btnPunto, btnSum, btnResta, btnDiv, btnMult, btnPar1, btnPar2, btnClear;


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
    iniciar();
    }
    private void iniciar() {
        btnUno = findViewById(R.id.btn1);
        btnDos = findViewById(R.id.btn2);
        btnTres = findViewById(R.id.btn3);
        btnVCuatro = findViewById(R.id.btn4);
        btnCinco = findViewById(R.id.btn5);
        btnSeis = findViewById(R.id.btn6);
        btnSiete = findViewById(R.id.btn7);
        btnOcho = findViewById(R.id.btn8);
        btnNueve = findViewById(R.id.btn9);
        btnCero = findViewById(R.id.btn0);
        btnResiduo = findViewById(R.id.btnResiduo);
        btnIgual = findViewById(R.id.btnIgual);
        btnPunto = findViewById(R.id.btnPunto);
        btnSum = findViewById(R.id.btnMas);
        btnResta = findViewById(R.id.btnMenos);
        btnDiv = findViewById(R.id.btnDiv);
        btnMult = findViewById(R.id.btnMult);
        btnPar1 = findViewById(R.id.btnPar1);
        btnPar2 = findViewById(R.id.btnPar2);
        btnClear = findViewById(R.id.btnClear);
        tvDisplay = findViewById(R.id.tvDisplay);

        //Escuchar los clicks
        btnUno.setOnClickListener(this);
        btnDos.setOnClickListener(this);
        btnTres.setOnClickListener(this);
        btnVCuatro.setOnClickListener(this);
        btnCinco.setOnClickListener(this);
        btnSeis.setOnClickListener(this);
        btnSiete.setOnClickListener(this);
        btnOcho.setOnClickListener(this);
        btnNueve.setOnClickListener(this);
        btnCero.setOnClickListener(this);
        btnResiduo.setOnClickListener(this);
        btnIgual.setOnClickListener(this);
        btnPunto.setOnClickListener(this);
        btnSum.setOnClickListener(this);
        btnResta.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnPar1.setOnClickListener(this);
        btnPar2.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnClear){
            clearDisplay();
        }else{
            concatenarCaracteres(id);
        }

    }
    public void clearDisplay(){
        tvDisplay.setText("");
    }

    public void concatenarCaracteres(int id){
        String display = tvDisplay.getText().toString();
        if (id == R.id.btn0){
            tvDisplay.setText(display + "0");
        } else if (id == R.id.btn1) {
            tvDisplay.setText(display + "1");
        } else if (id == R.id.btn2) {
            tvDisplay.setText(display + "2");
        } else if (id == R.id.btn3) {
            tvDisplay.setText(display + "3");
        } else if (id == R.id.btn4) {
            tvDisplay.setText(display + "4");
        } else if (id == R.id.btn5) {
            tvDisplay.setText(display + "5");
        } else if (id == R.id.btn6) {
            tvDisplay.setText(display + "6");
        } else if (id == R.id.btn7) {
            tvDisplay.setText(display + "7");
        } else if (id == R.id.btn8) {
            tvDisplay.setText(display + "8");
        } else if (id == R.id.btn9) {
            tvDisplay.setText(display + "9");
        } else if (id == R.id.btnPar1) {
            tvDisplay.setText(display + "(");
        } else if (id == R.id.btnPar2) {
            tvDisplay.setText(display + ")");
        } else if (id == R.id.btnPunto) {
            tvDisplay.setText(display + ".");
        } else if (id == R.id.btnMas) {
            tvDisplay.setText(display + "+");
        } else if (id == R.id.btnMenos) {
            tvDisplay.setText(display + "-");
        } else if (id == R.id.btnMult) {
            tvDisplay.setText(display + "*");
        } else if (id == R.id.btnDiv) {
            tvDisplay.setText(display + "/");
        } else if (id == R.id.btnResiduo) {
            tvDisplay.setText(display + "%");
        } else if (id == R.id.btnIgual) {
            List<String> texto = new ArrayList<>();
            String temp = "";
            for (char c : display.toCharArray()){
                if (Character.isDigit(c) || c == '.'){
                    temp += c;
                }else{
                    if (!temp.isEmpty()){
                        texto.add(temp);
                        temp = "";
                    }
                    texto.add(String.valueOf(c));

                }
            }

            if(!temp.isEmpty()){
                texto.add(temp);
            }

            while (texto.contains("(")) {
                int inicio = texto.lastIndexOf("(");
                int fin = -1;
                for (int i = inicio + 1; i < texto.size(); i++) {
                    if (texto.get(i).equals(")")) {
                        fin = i;
                        break;
                    }
                }

                if (fin != -1) {
                    List<String> subLista = new ArrayList<>(texto.subList(inicio + 1, fin));
                    String resultadoParentesis = operar(subLista);

                    texto.set(inicio, resultadoParentesis);

                    int elementosEliminar = fin - inicio;
                    for (int k = 0; k < elementosEliminar; k++) {
                        texto.remove(inicio + 1);
                    }
                } else {
                    break;
                }
            }

            String res = operar(texto);

            tvDisplay.setText(res);
        }
    }

    public String operar(List<String> texto){

        for (int i = 0; i < texto.size(); i++){
            String simbolo = texto.get(i);
            if (simbolo.equals("/") || simbolo.equals("*") || simbolo.equals("%")){
                if (i>0 && i< texto.size()-1){
                    float n1 = Float.parseFloat(texto.get(i - 1));
                    float n2 = Float.parseFloat(texto.get(i + 1));
                    float res = simbolo.equals("/") ? (n1/n2) : simbolo.equals("*") ? (n1*n2) : (n1%n2);

                    texto.set(i-1,String.valueOf(res));
                    texto.remove(i);
                    texto.remove(i);
                    i--;
                }
            }
        }
        for (int i = 0; i < texto.size(); i++){
            String simbolo = texto.get(i);
            if (simbolo.equals("+") || simbolo.equals("-")){
                if (i>0 && i< texto.size()-1){
                    float n1 = Float.parseFloat(texto.get(i - 1));
                    float n2 = Float.parseFloat(texto.get(i + 1));
                    float res = simbolo.equals("+") ? (n1+n2) : (n1-n2);

                    texto.set(i-1,String.valueOf(res));
                    texto.remove(i);
                    texto.remove(i);
                    i--;
                }
            }
        }

        return texto.get(0);
    }
}