package com.pdm.panaderia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pdm.panaderia.Adapter.Producto
import com.pdm.panaderia.Adapter.ProductoAdapter

class Catalogo : AppCompatActivity() {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var productosAdaptador: ProductoAdapter
    private var lista_productos: MutableList<Producto> = arrayListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        val btn_abajo = findViewById<FloatingActionButton>(R.id.item_inicio_abajo)
        btn_abajo.setOnClickListener {
            val inicioIntent: Intent = Intent(this, MainActivity::class.java).apply {
            }
            finish()
            startActivity(inicioIntent)
        }

        inicializar()
    }

    private fun inicializar() {
        recyclerProductos= findViewById(R.id.recycler)
        recyclerProductos.layoutManager = LinearLayoutManager(this)
        for(i in 1..3){
            val a=Producto(i,"Hola",i*2.0)
            lista_productos.add(a)
        }

        productosAdaptador = ProductoAdapter(lista_productos,this)
        recyclerProductos.adapter=productosAdaptador



    }

}