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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText jetCodigo, jetNombre, jetCiudad;
    RadioButton jrbProfesional, jrbAscenso, jrbAficionado;
    CheckBox jcbActivo;

    boolean respuesta;
    String codigo, nombre, ciudad, categoria, activo, identDoc;
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
            equipo.put("Activo", "si");

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

    public void consultar(View view) {
        buscarEquipo();
    }

    private void buscarEquipo() {
        respuesta = false;
        codigo = jetCodigo.getText().toString();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "El Codigo Es Requerido", Toast.LENGTH_LONG).show();
            jetCodigo.requestFocus();
        } else {
            db.collection("Campeonato")
                    .whereEqualTo("Codigo",codigo)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    respuesta = true;
                                    identDoc = document.getId();
                                    jetNombre.setText(document.getString("Nombre"));
                                    jetCiudad.setText(document.getString("Ciudad"));

                                    if (document.getString("Categoria").equalsIgnoreCase("Profesional")) {
                                        jrbProfesional.setChecked(true);
                                    } else if (document.getString("Categoria").equalsIgnoreCase("Ascenso")) {
                                        jrbAscenso.setChecked(true);
                                    } else {
                                        jrbAficionado.setChecked(true);
                                    }

                                    if (document.getString("Activo").equalsIgnoreCase("si")) {
                                        jcbActivo.setChecked(true);
                                    } else {
                                        jcbActivo.setChecked(false);
                                    }
                                }
                            } else {
                                //Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }

    public void modificar(View view) {
        codigo = jetCodigo.getText().toString();
        nombre = jetNombre.getText().toString();
        ciudad = jetCiudad.getText().toString();

        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()) {
            Toast.makeText(this, "Todos Los Campos Son Requeridos", Toast.LENGTH_LONG).show();
        } else if(respuesta) {
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
            equipo.put("Activo", "si");

            // Update a new document with a generated ID
            db.collection("Campeonato").document(identDoc)
                    .set(equipo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this,"Documento actualizado correctmente...",Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Error actualizando Documento...",Toast.LENGTH_SHORT).show();
                        }
                    });


        } else {
            Toast.makeText(this, "Debe Primero Consultar", Toast.LENGTH_LONG).show();
            jetCodigo.requestFocus();
        }
    }

    private void limpiarCampos() {
        jetCodigo.setText("");
        jetNombre.setText("");
        jetCiudad.setText("");

        jrbProfesional.setChecked(true);
        jcbActivo.setChecked(false);
        jetCodigo.requestFocus();
        respuesta= false;
    }
}