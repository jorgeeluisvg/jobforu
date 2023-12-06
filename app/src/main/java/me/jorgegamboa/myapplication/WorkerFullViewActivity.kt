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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.json.JSONObject

class WorkerFullViewActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var id_conv: Int = 0
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

            firebaseAuth = FirebaseAuth.getInstance()

            // Primero generamos una peticion GET para generar el idconversacion
            val id_trabajador = intent.getStringExtra("id_trabajador")
            Log.i("PFinal", id_trabajador.toString())
            val sender = firebaseAuth.currentUser?.uid.toString()
            val requestQueue = Volley.newRequestQueue(this)
            val url: String =
                "https://www.arucc.lat/appMobile/chats/index.php?id_user_1=$sender&id_user_2=$id_trabajador"
            val request: JsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    procesarRespuesta(response)
                    Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
                }, { error ->
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(request)

        }

    }

    private fun procesarRespuesta(response: JSONObject) {
        id_conv = response.getInt("id_conv")
        Log.i("valores3 before",id_conv.toString())
        val intent = Intent(this, ChatsActivos::class.java)
        startActivity(intent)
    }
}