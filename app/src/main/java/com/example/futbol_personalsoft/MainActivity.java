package com.example.futbol_personalsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText jetCodigo, jetNombre, jetCiudad;
    RadioButton jrbProfesional, jrbAscenso, jrbAficionado;
    CheckBox jcbActivo;

    String codigo, nombre, ciudad, categoria, activo;
    FirebaseFirestore db = FirebaseFirestore .getInstance();

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
        jrbProfesional.setChecked(true);
    }

    public void adicionar(View view) {
        codigo = jetCodigo.getText().toString();
        nombre = jetNombre.getText().toString();
        ciudad = jetCiudad.getText().toString();

        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()) {
            Toast.makeText(this, "Todos Los Campos Son Requeridos", Toast.LENGTH_LONG).show();
        } else {
            /* Que Categoria Es El Equipo */
            if (jrbProfesional.isChecked()) {
                categoria = "Profesional";
            } else if (jrbAscenso.isChecked()) {
                categoria = "Ascenso";
            } else {
                categoria = "Aficionado";
            }

            // Create a new user with a first and last name
            Map<String, Object> equipo = new HashMap<>();
            equipo.put("Codigo", codigo);
            equipo.put("Nombre", nombre);
            equipo.put("Ciudad", ciudad);
            equipo.put("Categoria", categoria);

            // Add a new document with a generated ID
            db.collection("Campeonato")
                    .add(equipo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MainActivity.this, "Documento Adicionado", Toast.LENGTH_LONG).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w("Error adding document", e);
                            Toast.makeText(MainActivity.this, "Documento NO Adicionado", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void limpiarCampos() {
        jetCodigo.setText("");
        jetNombre.setText("");
        jetCiudad.setText("");

        jrbProfesional.setChecked(true);
        jcbActivo.setChecked(false);
        jetCodigo.requestFocus();
    }
}