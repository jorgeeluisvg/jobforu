package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MyProfile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var etEditarNombre : EditText
    private lateinit var etEditarApellido : EditText
    private lateinit var etEditarUbi : EditText
    private lateinit var etEditarDescr : EditText
    private lateinit var etEditarOficio : EditText
    private lateinit var etEditarTelefono : EditText
    private lateinit var ivEditarPerfil : ImageView

    private lateinit var bActualizarPerfil : Button


    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        // Edit texts

         etEditarNombre = findViewById(R.id.etEditarNombre)
         etEditarApellido = findViewById(R.id.etEditarApellido)
         etEditarUbi = findViewById(R.id.etEditarUbi)
         etEditarDescr = findViewById(R.id.etEditarDescr)
         etEditarOficio = findViewById(R.id.etEditarOficio)
         etEditarTelefono = findViewById(R.id.etEditarTelefono)

        // Imagen
        ivEditarPerfil = findViewById(R.id.ivEditarPerfil)

        // Boton
        bActualizarPerfil = findViewById(R.id.bActualizarPerfil)

        bActualizarPerfil.setOnClickListener {
            // Actualizar Datos

            firebaseAuth = FirebaseAuth.getInstance()
            val id_user = firebaseAuth.currentUser?.uid

            val nombre = etEditarNombre.text.toString()
            val apellido = etEditarApellido.text.toString()
            val ubicacion = etEditarUbi.text.toString()
            val descripcion = etEditarDescr.text.toString()
            val oficio = etEditarOficio.text.toString()

            // Construimos el JSONObject
            val jsonParams = JSONObject()
            jsonParams.put("id_user", id_user)
            jsonParams.put("nombre", nombre)
            jsonParams.put("apellido", apellido)
            jsonParams.put("ubicacion", ubicacion)
            jsonParams.put("descripcion", descripcion)
            jsonParams.put("oficio", oficio)

            val url = "https://www.arucc.lat/appMobile/profiles/index.php?"
            val request = JsonObjectRequest(
                Request.Method.POST,  // Cambiado a POST
                url,
                jsonParams,
                { response ->
                    // Manejar la respuesta exitosa
                    Toast.makeText(this, "Solicitud exitosa", Toast.LENGTH_SHORT).show()
                    // Si fue exitoso lo mandamos al homepage
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                },
                { error ->
                    // Manejar errores de la solicitud
                    Toast.makeText(this, "Error en la solicitud: $error", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)

        }
    }


    override fun onStart() {
        super.onStart()
        ObtenerInfo()
    }

    private fun ObtenerInfo() {
        firebaseAuth = FirebaseAuth.getInstance()
        val id_user = firebaseAuth.currentUser?.uid
        val requestQueue = Volley.newRequestQueue(this)
        val url : String = "https://www.arucc.lat/appMobile/profiles/index.php?id_user=$id_user"
        val request : JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                ProcesarRespuesta(response,etEditarNombre,etEditarApellido,
                    etEditarUbi,etEditarDescr,etEditarOficio,etEditarTelefono)
                Toast.makeText(this,"Correcto", Toast.LENGTH_SHORT).show()
            }, { error ->
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    private fun ProcesarRespuesta(
        response: JSONObject,
        etEditarNombre: EditText,
        etEditarApellido: EditText,
        etEditarUbi: EditText,
        etEditarDescr: EditText,
        etEditarOficio: EditText,
        etEditarTelefono: EditText
    ) {
        // Obtener datos del objeto JSON de respuesta
        val profile = response.getJSONArray("Profile").getJSONObject(0)
        val nombre = profile.getString("Nombre")
        val apellido = profile.getString("Apellido")
        val ubicacion = profile.getString("Ubicacion")
        val descripcion = profile.getString("Descripcion")
        val oficio = profile.getString("Oficio")
        val telefono = profile.getString("telefono")


        // obtener imagen perfil y mostrarla
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Obtén la referencia al Storage
        val storageReference = FirebaseStorage.getInstance().reference

        // Obtén la referencia a la imagen utilizando el UID del usuario
        val imageReference = storageReference.child("images/$uid/foto")

        // Descarga la URL de la imagen
        imageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // La URL de la imagen está disponible
                val imageUrl = task.result.toString()


                // Carga la imagen en el ImageView utilizando Picasso
                Picasso.get()
                    .load(imageUrl)
                    .rotate(270F)
                    .into(ivEditarPerfil)  // 'imageView' es tu ImageView en el diseño XML
            } else {
                // Maneja el error al obtener la URL de la imagen
                Toast.makeText(this, "Error al obtener la URL de la imagen: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }


        // Actualizar los EditText con los datos obtenidos
        etEditarNombre.setText(nombre)
        etEditarApellido.setText(apellido)
        etEditarUbi.setText(ubicacion)
        etEditarDescr.setText(descripcion)
        etEditarOficio.setText(oficio)
        etEditarTelefono.setText(telefono)
    }
}