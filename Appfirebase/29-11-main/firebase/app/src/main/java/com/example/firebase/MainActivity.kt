package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var pass:EditText
    private lateinit var entrar:Button
    private lateinit var registrar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       email=findViewById(R.id.txt_user)
        pass=findViewById(R.id.txt_pass)
        entrar=findViewById(R.id.entrar)
        registrar=findViewById(R.id.registrar)

      ejecutaranalitica()
        setup()
    }
    fun ejecutaranalitica(){
        val analitic:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mensaje","integracion de firebase completas")
        analitic.logEvent("InitScreen",bundle)
    }

    fun setup(){
    title = "Autentificacion"
    registrar.setOnClickListener{
        if (email.text.isNotEmpty()&& pass.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    mostrarprincipal(it?.result?.user?.email?:"",TiposProveedor.BASICO)
                }else{
                    mostraralerta()
                }
            }
        }
    }
        entrar.setOnClickListener{
            if (email.text.isNotEmpty()&& pass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        mostrarprincipal(it?.result?.user?.email?:"",TiposProveedor.BASICO)
                    }else{
                        mostraralerta()
                    }
                }
            }
        }
    }

    fun mostraralerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error de coneccion")
        builder.setMessage("se a producido un error")
        builder.setPositiveButton("aceptar",null)
        val dialog=builder.create()
        dialog.show()
    }

    fun mostrarprincipal(email:String,proveedor:TiposProveedor){
        val ventana:Intent = Intent(this,MainActivity2::class.java).apply {
            putExtra("email",email)
            putExtra("proveedor",proveedor.name.toString())
        }
        startActivity(ventana)

    }

    enum class TiposProveedor{
        BASICO
    }
}

