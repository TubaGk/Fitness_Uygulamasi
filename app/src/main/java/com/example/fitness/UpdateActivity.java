package com.example.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {
    EditText useHght, userWght;
    TextView sonuc;
    Button hesaplaBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        useHght=findViewById(R.id.userheightxt);
        userWght=findViewById(R.id.userweighttxt);
        hesaplaBtn =findViewById(R.id.update);
        backBtn=findViewById(R.id.cancel);
        sonuc=findViewById(R.id.sonuc);


        hesaplaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void calculateBMI() {
        String boyStr = useHght.getText().toString();
        String kiloStr = userWght.getText().toString();
        if (!boyStr.isEmpty() && !kiloStr.isEmpty()) {
            float boy = Float.parseFloat(boyStr)/100;
            float kilo =Float.parseFloat(kiloStr);
            float sonuccp = kilo / (boy * boy);
            String result = "Sonuç: " + String.format("%.2f", sonuccp) + "\n";
            if (sonuccp < 18.5) {
                sonuc.setText("BMI Sonucunuz: " +result + "Zayıfsınız.");
            } else if (sonuccp < 25) {
                sonuc.setText("BMI Sonucunuz: " +result + "Normalsiniz.");
            } else if (sonuccp < 30) {
                sonuc.setText("BMI Sonucunuz: " +result+ "Kilolusunuz.");
            } else {
                sonuc.setText("BMI Sonucunuz: " +result + "Obezsiniz.");
            }
        } else {
            sonuc.setText("Lütfen boy ve kilo bilgilerini girin.");
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}