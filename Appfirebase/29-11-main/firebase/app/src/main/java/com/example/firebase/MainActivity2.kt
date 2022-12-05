package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {
    private lateinit var txt_user:TextView
    private lateinit var txt_prove:TextView
    private lateinit var cerrar:Button
    private lateinit var nombre:EditText
    private lateinit var telefono:EditText
    private lateinit var municipio:EditText
    private lateinit var guardar:Button
    private lateinit var eliminar:Button
    private lateinit var consultar:Button
    val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        txt_user = findViewById(R.id.txt_user2)
        txt_prove = findViewById(R.id.txt_pro)
        cerrar = findViewById(R.id.button2)
        nombre = findViewById(R.id.nombre)
        municipio = findViewById(R.id.municipio)
        telefono = findViewById(R.id.tel)
        guardar = findViewById(R.id.btnGuardar)
        consultar = findViewById(R.id.btnConsultar)
        eliminar = findViewById(R.id.btnBorrar)
        var buldle:Bundle? = intent.extras


        var emal = buldle?.getString("email")
        var prover= buldle?.getString("proveedor")

        setup(emal?:"",prover?:"")
    }

  fun setup(email:String,Prove:String){
        title="Principal"

        txt_user.text = email
        txt_prove.text = Prove

        cerrar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

      guardar.setOnClickListener{
          bd.collection("usuarios").document(email).set(
              hashMapOf(
                  "poveedor" to Prove,
                  "nombre" to nombre.text.toString(),
                  "nombre" to telefono.text.toString(),
                  "nombre" to municipio.text.toString(),
              )
          )
      }
      consultar.setOnClickListener{
        bd.collection("usuarios").document(email).get().addOnSuccessListener {
            nombre.setText(it.getString("nombre") as String)
            telefono.setText(it.getString("telefono") as String)
            municipio.setText(it.getString("municipio") as String)
        }
      }
      cerrar.setOnClickListener{
          FirebaseAuth.getInstance().signOut()
          onBackPressed()
      }
      cerrar.setOnClickListener{
          FirebaseAuth.getInstance().signOut()
          onBackPressed()
      }
    }


}