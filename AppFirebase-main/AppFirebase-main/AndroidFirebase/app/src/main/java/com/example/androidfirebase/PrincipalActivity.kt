package com.example.androidfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class PrincipalActivity : AppCompatActivity() {
    private lateinit var btnCerrar:Button
    private lateinit var tvCorreo:TextView
    private lateinit var tvProveedor:TextView
    private lateinit var etNombre:EditText
    private lateinit var etTelefono:EditText
    private lateinit var etMunicipio:EditText
    private lateinit var btnGuardar:Button
    private lateinit var btnConsultar:Button
    private lateinit var btnBorrar:Button
    val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        btnCerrar = findViewById(R.id.btn_cerrar)
        tvCorreo = findViewById(R.id.tv_Email)
        tvProveedor = findViewById(R.id.tv_proveedor)
        etNombre = findViewById(R.id.et_nombre)
        etTelefono = findViewById(R.id.et_Fon)
        etMunicipio = findViewById(R.id.et_Municipito)
        btnGuardar = findViewById(R.id.btn_Guardar)
        btnConsultar = findViewById(R.id.btn_Consultar)
        btnBorrar = findViewById(R.id.btn_Borrar)

        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("Email")
        val Pass:String? = bundle?.getString("proveedor")


        setup(email?:"",Pass?:"")
    }

    fun setup(Email:String, proveedor:String)
    {
        title = "Principal"
        tvCorreo.text = Email
        tvProveedor.text = proveedor

        btnCerrar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        btnGuardar.setOnClickListener{
          bd.collection("usuarios").document(Email).set(
              hashMapOf("proveedor" to proveedor,
              "nombre" to etNombre.text.toString(),
              "telefono" to etTelefono.text.toString(),
              "municipio" to etMunicipio.text.toString()
              )
          )
        }

        btnConsultar.setOnClickListener{

            bd.collection("usuarios").document(Email).get().addOnSuccessListener {
                etNombre.setText(it.getString("nombre") as String?)
                etTelefono.setText(it.getString("telefono") as String?)
                etMunicipio.setText(it.getString("municipio") as String?)
            }
        }

        btnBorrar.setOnClickListener{
bd.collection("usarios").document(Email).delete()
        }
    }
}