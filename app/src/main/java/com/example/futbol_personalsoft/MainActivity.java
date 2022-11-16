package com.example.futbol_personalsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText jetCodigo, jetNombre, jetCiudad;
    RadioButton jrbProfesional, jrbAscenso, jrbAficionado;
    CheckBox jcbActivo;

    String codigo, nombre, ciudad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jetCodigo = findViewById(R.id.etCodigo);
        jetNombre = findViewById(R.id.etNombre);
        jetCiudad = findViewById(R.id.etCiudad);

        jrbProfesional = findViewById(R.id.rbProfesional);
        jrbAscenso = findViewById(R.id.rbAscenso);
        jrbAficionado = findViewById(R.id.rbAficionado);

        jcbActivo = findViewById(R.id.cbActivo);
    }

    public void adicionar(View view) {
        codigo = jetCodigo.getText().toString();
        nombre = jetNombre.getText().toString();
        ciudad = jetCiudad.getText().toString();

        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()) {
            Toast.makeText(this, "Todos Los Campos Son Requeridos", Toast.LENGTH_LONG).show();
        } else {

        }
    }
}