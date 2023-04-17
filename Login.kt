package com.pdm.panaderia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    private lateinit var btn_registro: Button
    private lateinit var btn_acceso: Button
    private lateinit var btn_google: Button

    private lateinit var btn_inicio: FloatingActionButton

    private lateinit var email: EditText
    private lateinit var passwrd: EditText

    private lateinit var client: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_inicio=findViewById(R.id.item_inicio_abajo)
        btn_inicio.setOnClickListener{
            goInicio("")
        }


        setup()
    }

    private fun setup(){
        title= "Login"

        btn_registro=findViewById(R.id.button_registro)
        //VALOR NULO
        email= findViewById(R.id.email)
        passwrd = findViewById(R.id.password)
        btn_registro.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        btn_acceso=findViewById(R.id.button_acceso)
        btn_acceso.setOnClickListener {
            if(email.text.isNotEmpty() && passwrd.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString(),passwrd.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            goInicio(it.result?.user?.email ?: "")
                        }else{
                            showAlert("Error al iniciar sesión")
                        }
                    }

            }else{
                showAlert("Los campos no pueden estar vacíos")
            }
        }

        val options= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client= GoogleSignIn.getClient(this,options)
        btn_google=findViewById(R.id.button_google)
        btn_google.setOnClickListener {
            val intent = client.signInIntent
            startActivityForResult(intent,10001)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10001){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)

            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        var map : HashMap<String, String>
                                = HashMap<String, String> ()
                        map.put("nombre",FirebaseAuth.getInstance().currentUser?.displayName.toString())
                        map.put("email",FirebaseAuth.getInstance().currentUser?.email.toString())
                        val id = FirebaseAuth.getInstance().currentUser?.uid
                        if (id != null) {
                            FirebaseDatabase.getInstance().reference.child("Users").child(id).setValue(map).addOnCompleteListener {
                                if(it.isSuccessful){
                                    goInicio(task.result?.user?.email ?: "")
                                }else{
                                    showAlert("Error al registrar en la base de datos")
                                }
                            }
                        }
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val i = Intent(this, Profile_settings::class.java)
            startActivity(i)
        }
    }



    private fun showAlert(string: String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(string)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

    private fun goInicio(email: String){
        val inicioIntent: Intent= Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
        }
        finish()
        startActivity(inicioIntent)
    }
}