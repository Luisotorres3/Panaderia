package com.pdm.panaderia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Objects

class Register : AppCompatActivity() {
    private lateinit var btn_registro: Button
    private lateinit var btn_inicio: FloatingActionButton

    private lateinit var edit_nombre: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_passwrd: EditText

    private var nombre: String = " "
    private var email: String = " "
    private var passwd: String= " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_inicio=findViewById(R.id.item_inicio_abajo)
        btn_inicio.setOnClickListener{
            goInicio("")
        }

        setup()
    }

    private fun setup() {
        title = "Login"

        btn_registro = findViewById(R.id.button_registro)
        //VALOR NULO
        edit_nombre = findViewById(R.id.nombre)
        edit_email = findViewById(R.id.email)
        edit_passwrd = findViewById(R.id.password)


        btn_registro.setOnClickListener {
            nombre = edit_nombre.text.toString()
            email = edit_email.text.toString()
            passwd = edit_passwrd.text.toString()



            if (nombre.isNotEmpty() && email.isNotEmpty() && passwd.isNotEmpty()) {
                if(passwd.length >= 6){
                    registerUser()
                }else{
                    showAlert("La contraseña debe tener al menos 6 caracteres")
                }

            } else {
                showAlert("Los campos no pueden estar vacíos")
            }
        }


    }

    private fun registerUser(){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,passwd)
            .addOnCompleteListener {task->
                if (task.isSuccessful) {
                    var map : HashMap<String, String>
                            = HashMap<String, String> ()
                    map.put("nombre",nombre)
                    map.put("email",email)
                    map.put("password",passwd)
                    val id = FirebaseAuth.getInstance().currentUser?.uid
                    showAlert(id.toString())
                    if (id != null) {
                        FirebaseDatabase.getInstance().reference.child("Users").child(id).setValue(map).addOnCompleteListener {
                            if(it.isSuccessful){
                                goInicio(task.result?.user?.email ?: "")
                            }else{
                                showAlert("Error al registrar en la base de datos")
                            }
                        }
                    }

                } else {
                    showAlert("Error al registrar")
                }
            }
    }

    private fun showAlert(string: String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(string)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun goInicio(email: String){
        val inicioIntent: Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
        }
        finish()
        startActivity(inicioIntent)
    }
}