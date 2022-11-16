package com.example.futbol_personalsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    String codigo, nombre, ciudad;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("first", "Ada");
            user.put("last", "Lovelace");
            user.put("born", 1815);

            // Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }
}