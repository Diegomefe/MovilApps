package com.upiiz.dm_jdmf_06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBasicLV;
    Button btnCustomLV;
    Button btnBasicRecyclerView;
    Button btnCustomRecyclerView;

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

        initComponents();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnBasicListView){
            iniciarBasicListView();
        } else if(v.getId() == R.id.btnCustomListView){
            iniciarCustomListView();
        } else if (v.getId() == R.id.btnBasicRecyclerView) {
            iniciarBasicRecyclerView();
        } else if (v.getId() == R.id.btnCustomRecyclerView) {
            iniciarCustomRecyclerView();
        }

    }

    public void iniciarBasicListView(){
        // En los intents podemos pasar parametros
        Intent intentBLV = new Intent(this, BasicListViewActivity.class);
        startActivity(intentBLV);
    }

    public void iniciarCustomListView(){
        Intent intentCLV = new Intent(this, CustomListViewActivity.class);
        startActivity(intentCLV);
    }

    public void iniciarBasicRecyclerView(){
        Intent intentBRV = new Intent(this, BasicRecylerViewActivity.class);
        startActivity(intentBRV);

    }

    public void iniciarCustomRecyclerView(){
        Intent intentCRV = new Intent(this, CustomRecyclerViewActivity.class);
        startActivity(intentCRV);
    }


    public void initComponents(){
        btnBasicLV = findViewById(R.id.btnBasicListView);
        btnCustomLV = findViewById(R.id.btnCustomListView);
        btnBasicRecyclerView = findViewById(R.id.btnBasicRecyclerView);
        btnCustomRecyclerView = findViewById(R.id.btnCustomRecyclerView);

        //Listener
        btnBasicLV.setOnClickListener(this);
        btnCustomLV.setOnClickListener(this);
        btnBasicRecyclerView.setOnClickListener(this);
        btnCustomRecyclerView.setOnClickListener(this);
    }
}