package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class WorkerFullViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workerprofile)

        if(intent != null){
            configUI()

        }
    }

    private fun configUI() {

        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val id_trabajador = intent.getStringExtra("id_trabajador")


        val tvNombre : TextView = findViewById(R.id.tvNombreFV)
        tvNombre.text = nombre

        val etDescripcionFV : EditText = findViewById(R.id.etDescripcionFV)
        etDescripcionFV.setText(descripcion.toString())

        val bChat : Button = findViewById(R.id.bchat)
        bChat.setOnClickListener {
            val intent = Intent(this, Chat::class.java)
            intent.putExtra("id_trabajador",id_trabajador)
            startActivity(intent)
        }

    }
}