package com.example.androidfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    //variables
    private lateinit var etMail:EditText
    private lateinit var etPass:EditText
    private lateinit var btnRegistro:Button
    private lateinit var btnLogin:Button
    private lateinit var llautenticar:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //vinculacion de variables
        etMail = findViewById(R.id.et_Email)
        etPass = findViewById(R.id.et_Pass)
        btnRegistro = findViewById(R.id.btn_Registrar)
        btnLogin = findViewById(R.id.btn_Login)
        llautenticar = findViewById(R.id.Ll_autenticar)

        //llamado de funciones
        ejecutarAnalitica()
        setupp()
    }


    //Con esto podriamos decirle a firebaseAnalitic si tiene interaccion con la aplicacion
    //Osea con etas lineas firebase registra la actividad de los usuarios que se conectan a la aplicacion que esta conectada con firebase
    fun ejecutarAnalitica()
    {
        val analicis:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mensaje","Integracion de firebase completado")
        analicis.logEvent("InitScreen",bundle)
    }

    //funcion para boton de
    fun setupp()
    {title = "Autentificacion"

        //llamamos al boton y verificamos si los campos de texto no estan vacios
        btnRegistro.setOnClickListener{
            if(etMail.text.isNotEmpty()&&etPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etMail.text.toString(),etPass.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        mostrarPrincipal(it?.result?.user?.email?:"", proveedor = TiposProveedor.BASICO)
                    }else{
                        moostrarAlerta()
                    }
                    }
                }
    }
        btnLogin.setOnClickListener{
            if(etMail.text.isNotEmpty()&&etPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etMail.text.toString(),etPass.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        mostrarPrincipal(it?.result?.user?.email?:"", proveedor = TiposProveedor.BASICO)
                    }else{
                        moostrarAlerta()
                    }
                }
            }
        }

    }


    //configuracion para mostrar mensajes de errores
    fun moostrarAlerta()
    {val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de conexion")
        builder.setMessage("Se a producido un error de autenticacion")
        builder.setPositiveButton("Aceptar",null)
        val dialogo = builder.create()
        dialogo.show()
    }
    //En esto se define los parametros que se cargaran en la siguiente activity
    fun mostrarPrincipal(email:String,proveedor:TiposProveedor) {
        val ventana:Intent = Intent(this,PrincipalActivity::class.java).apply {
            putExtra("Email",email)
            putExtra("proveedor",proveedor.name.toString())
        }
        startActivity(ventana)
    }
}
enum class TiposProveedor
{
    BASICO
}


