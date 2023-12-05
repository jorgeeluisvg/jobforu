package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

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

        // TextViewa
        val tvNombre : TextView = findViewById(R.id.tvNombreFV)
        tvNombre.text = nombre

        // Image View
        val ivWorkerProfile: ImageView = findViewById(R.id.ivWorkerProfile)

        val etDescripcionFV : EditText = findViewById(R.id.etDescripcionFV)
        etDescripcionFV.setText(descripcion.toString())

        // obtener imagen perfil y mostrarla
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Obtén la referencia al Storage
        val storageReference = FirebaseStorage.getInstance().reference

        // Obtén la referencia a la imagen utilizando el UID del usuario
        val imageReference = storageReference.child("images/$id_trabajador/foto")

        // Descarga la URL de la imagen
        imageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // La URL de la imagen está disponible
                val imageUrl = task.result.toString()

                // Carga la imagen en el ImageView utilizando Picasso
                Picasso.get()
                    .load(imageUrl)
                    .rotate(270f)
                    .into(ivWorkerProfile)  // 'imageView' es tu ImageView en el diseño XML
            } else {
                // Maneja el error al obtener la URL de la imagen
                Toast.makeText(this, "Error al obtener la URL de la imagen: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }

        val bChat : Button = findViewById(R.id.bchat)
        bChat.setOnClickListener {
            val intent = Intent(this, Chat::class.java)
            intent.putExtra("id_trabajador",id_trabajador)
            startActivity(intent)
        }

    }
}