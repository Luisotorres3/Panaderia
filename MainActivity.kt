package com.pdm.panaderia

import android.content.ClipData
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.TextElementListener
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mehdi.sakout.aboutpage.AboutPage

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var navBar: BottomNavigationView
    private lateinit var title: TextView
    private lateinit var icono: ImageView
    private lateinit var item_abajo: MenuItem
    private lateinit var botonInicio: FloatingActionButton

    private lateinit var aux: BottomNavigationView.OnNavigationItemReselectedListener
    private lateinit var linkTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frame1, Inicio.newInstance("", "")).commit()

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        title=findViewById(R.id.toolbar_title)

        navBar=findViewById(R.id.bottomNavBar)
        item_abajo=navBar.menu.findItem(R.id.holder)
        item_abajo.setChecked(true)
        navBar.background=null

        botonInicio=findViewById(R.id.item_inicio_abajo)
        botonInicio.setOnClickListener {
            title.setText("Inicio")
            supportFragmentManager.beginTransaction().replace(R.id.frame1, Inicio.newInstance("", "")).commit()
            item_abajo.setChecked(true)
            true
        }

        navBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_inicio_abajo_perfil-> {
                    val intent= Intent(this,Login::class.java).apply {
                        //putExtra("email",email)
                    }
                    startActivity(intent)
                    true
                }
                R.id.item_inicio_abajo_catalogo-> {
                    val intent= Intent(this,Catalogo::class.java).apply {
                        //putExtra("email",email)
                    }
                    startActivity(intent)
                    true
                }
                R.id.item_inicio_abajo_cesta-> {
                    title.setText("Cesta")
                    supportFragmentManager.beginTransaction().replace(R.id.frame1, Cesta.newInstance("","")).commit()
                    true
                }
                R.id.item_inicio_abajo_contacto-> {
                    title.setText("Contacto")
                    supportFragmentManager.beginTransaction().replace(R.id.frame1, Contacto.newInstance("","")).commit()
                    true
                }
                else -> false
            }
        }
        irAPerfil()
        irACatalogo()
        irACesta()
        irAContacto()

        val bundle=intent.extras
        val email= bundle?.getString("email")
        setup(email ?: "")


    }

    private fun setup(email: String){
        val textviewInicio: TextView=findViewById(R.id.text_email)
        textviewInicio.setText(email)


        //Logout
    }

    public fun irAPerfil(){
        var btn_perfil: Button
        btn_perfil=findViewById(R.id.buttonPerfil)
        btn_perfil.setOnClickListener {
            val intent= Intent(this,Login::class.java).apply {
                //putExtra("email",email)
            }
            finish()
            startActivity(intent)
            true
        }

    }
    public fun irACatalogo(){
        var btn: Button
        btn=findViewById(R.id.buttonCatalogo)
        btn.setOnClickListener {
            val intent= Intent(this,Catalogo::class.java).apply {
                //putExtra("email",email)
            }
            finish()
            startActivity(intent)
            true
            navBar.menu.findItem(R.id.item_inicio_abajo_catalogo).setChecked(true)

        }
    }
    public fun irAContacto(){
        var btn: Button
        btn=findViewById(R.id.buttonContacts)
        btn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame1, Contacto.newInstance("","")).commit()
            navBar.menu.findItem(R.id.item_inicio_abajo_contacto).setChecked(true)

        }

    }
    public fun irACesta(){
        var btn: Button
        btn=findViewById(R.id.buttonCesta)
        btn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame1, Cesta.newInstance("","")).commit()
            navBar.menu.findItem(R.id.item_inicio_abajo_cesta).setChecked(true)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_inicio,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            /*R.id.item_inicio -> {
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item_perfil -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame1, Perfil.newInstance("","")).commit()
                //supportActionBar?.setDisplayHomeAsUpEnabled(true)
                true
            }
            R.id.item_catalogo -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame1, Catalogo.newInstance("","")).commit()
                true
            }

*/
        }
        return super.onOptionsItemSelected(item)
    }


}