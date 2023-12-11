package com.example.sharedbilltipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText price;
    Spinner spinner;
    EditText nb_people;
    EditText tip;
    TextView msgError;

    Button calculer;
    Double tipvalue;
    String spinnerType;
    Double total_Amount;
    Double tip_person;
    Double total_person;

    Double priceValue;
    Double number_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        price= findViewById(R.id.price);
        spinner=findViewById(R.id.spinner1);
        nb_people=findViewById(R.id.nbpeople);
        tip=findViewById(R.id.tip1);
        calculer=findViewById(R.id.calcul);
        msgError=findViewById(R.id.error);

        String[] items = new String[]{"DH", "%"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                if(Objects.equals(selectedItem, "DH")){
                    spinnerType="DH";
                }
                else{
                    spinnerType="%";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerType="DH";
            }

        });

        calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!price.getText().toString().isEmpty() && !nb_people.getText().toString().isEmpty() && !tip.getText().toString().isEmpty()){
                    priceValue = Double.valueOf(price.getText().toString());
                    number_person= Double.valueOf(nb_people.getText().toString());
                    if(Objects.equals(spinnerType, "DH")){
                        tipvalue = Double.valueOf(tip.getText().toString());

                    }
                    else{
                        Double porcentageValue= Double.valueOf(tip.getText().toString());
                        tipvalue=(priceValue*(porcentageValue))/100;
                        tipvalue = Double.valueOf(String.format("%.2f", tipvalue));
                    }

                    total_Amount=tipvalue+priceValue;
                    tip_person=tipvalue/number_person;
                    tip_person = Double.valueOf(String.format("%.2f", tip_person));
                    total_person=total_Amount/number_person;
                    total_person = Double.valueOf(String.format("%.2f", total_person));

                    Intent i= new Intent(MainActivity.this,Resultat.class);
                    i.putExtra("total",total_Amount);
                    i.putExtra("tip",tipvalue);
                    i.putExtra("tipPerson",tip_person);
                    i.putExtra("totalPerson",total_person);

                    price.setText("");
                    tip.setText("");
                    nb_people.setText("");
                    msgError.setText("");


                    startActivityForResult(i,1);
                }
                else {
                    //Toast.makeText(MainActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    msgError.setText("Please fill all fields.");
                }
            }

        });


    }
}
