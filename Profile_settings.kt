package com.pdm.panaderia

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase


class Profile_settings : AppCompatActivity() {

    private lateinit var btn_logout: Button
    private lateinit var btn_cambiar_nombre: Button
    private lateinit var btn_cambiar_email: Button
    private lateinit var btn_cambiar_password: Button
    private lateinit var btn_eliminar_cuenta: Button


    private lateinit var text_nombre: TextView
    private lateinit var text_email: TextView
    private lateinit var edit_email: EditText
    private lateinit var edit_nombre: EditText
    private lateinit var edit_password: EditText

    private lateinit var id: String
    private lateinit var email: String
    private lateinit var nombre: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)
        this.title="Perfil"

        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Users")

        val btn_abajo = findViewById<FloatingActionButton>(R.id.item_inicio_abajo)
        btn_abajo.setOnClickListener {
            val inicioIntent: Intent= Intent(this, MainActivity::class.java).apply {
            }
            finish()
            startActivity(inicioIntent)
        }

        id= FirebaseAuth.getInstance().uid.toString()

        referencia.child(id).get().addOnCompleteListener {
            if(it.isSuccessful){
                if(it.result.exists()){
                    val dataSnapshot:DataSnapshot = it.result
                    text_nombre.setText("Bienvenido "+dataSnapshot.child("nombre").getValue().toString()+"!")
                    text_email.setText(dataSnapshot.child("email").getValue().toString())
                    edit_nombre.setText(dataSnapshot.child("nombre").getValue().toString())
                    edit_email.setText(dataSnapshot.child("email").getValue().toString())
                    edit_password.setText(dataSnapshot.child("password").getValue().toString())

                    //edit_password.setText(FirebaseAuth.getInstance().currentUser?.email.toString())


                }else{
                    Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Error al leer", Toast.LENGTH_SHORT).show()
            }
        }


        edit_email=findViewById(R.id.profile_email_2)
        btn_cambiar_email=findViewById(R.id.button_cambiar_email)
        btn_cambiar_email.setOnClickListener {
            hacerVisible(edit_email)
        }

        edit_nombre=findViewById(R.id.profile_name)
        btn_cambiar_nombre=findViewById(R.id.button_cambiar_nombre)
        btn_cambiar_nombre.setOnClickListener {
            hacerVisible(edit_nombre)
        }

        edit_password=findViewById(R.id.profile_password)
        btn_cambiar_password=findViewById(R.id.button_cambiar_password)
        btn_cambiar_password.setOnClickListener {
            hacerVisible(edit_password)
        }

        text_nombre=findViewById(R.id.text_nombre_profile)
        text_email=findViewById(R.id.text_email_profile_2)


        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val user = FirebaseAuth.getInstance().currentUser!!
                        val id = user.uid
                        user.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
                                    databaseReference.child(id).removeValue()
                                    Log.d(TAG, "User account deleted.")
                                    val intent = Intent(this, Login::class.java)
                                    startActivity(intent)
                                }
                            }

                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        //dialog.dismiss()
                    }
                }
            }

        btn_eliminar_cuenta=findViewById(R.id.button_eliminar_cuenta)
        btn_eliminar_cuenta.setOnClickListener {

            val ab: AlertDialog.Builder = AlertDialog.Builder(this)
            ab.setMessage("Estás seguro?").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

        }




        btn_logout=findViewById(R.id.button_enviar)
        btn_logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun hacerVisible(edit_text: EditText){
        if(edit_text.isVisible){
            edit_text.visibility= View.GONE
        }else{
            edit_text.visibility= View.VISIBLE
        }
    }
}