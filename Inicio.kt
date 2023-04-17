package com.pdm.panaderia

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Inicio.newInstance] factory method to
 * create an instance of this fragment.
 */
class Inicio : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }
    private lateinit var btn_perfil : Button
    private lateinit var btn_catalogo : Button
    private lateinit var btn_cesta : Button
    private lateinit var btn_contacto : Button

    private lateinit var text_email: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root: View
        root=inflater.inflate(R.layout.fragment_inicio, container, false)
        btn_perfil=root.findViewById(R.id.buttonPerfil)
        btn_perfil.setOnClickListener {
            val intent= Intent(activity,Login::class.java).apply {
                //putExtra("email",email)
            }

            startActivity(intent)
            true
        }

        btn_catalogo=root.findViewById(R.id.buttonCatalogo)
        btn_catalogo.setOnClickListener {
            val intent= Intent(activity,Catalogo::class.java).apply {
                //putExtra("email",email)
            }
            startActivity(intent)
            true
        }

        btn_cesta=root.findViewById(R.id.buttonCesta)
        btn_cesta.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame1, Cesta.newInstance("","")).commit()
        }

        btn_contacto=root.findViewById(R.id.buttonContacts)
        btn_contacto.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame1, Contacto.newInstance("","")).commit()
        }

        text_email = root.findViewById(R.id.text_email)
        val id:String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        FirebaseDatabase.getInstance().getReference().child(id).get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    val dataSnapshot: DataSnapshot = it.result
                    text_email.setText(
                        "Bienvenido " + dataSnapshot.child("nombre").getValue().toString() + "!"
                    )
                }
            }
        }
        return root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Inicio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Inicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}