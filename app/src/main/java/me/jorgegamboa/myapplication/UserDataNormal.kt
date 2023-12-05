package me.jorgegamboa.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import org.json.JSONObject

class UserDataNormal : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_data_normal)

        firebaseAuth = FirebaseAuth.getInstance()
        // EditTexts
        val etRegistroNombre: EditText = findViewById(R.id.etRegistroNombre3)
        val etRegistroApellido: EditText = findViewById(R.id.etRegistroApellido3)
        val etRegistroUbi: EditText = findViewById(R.id.etRegistroUbicacion3)
        val etRegistroDescr: EditText = findViewById(R.id.etRegistroDescripcion3)
        val etRegistroPhone: EditText = findViewById(R.id.etRegistroTelefono3)
        val bUserData : Button = findViewById(R.id.bUserData)
        val bSubirImagen : Button = findViewById(R.id.bSubirImagen2)

        // Declaramos conexion de storage en firebase
        storage = FirebaseStorage.getInstance()

        // Este es como el intent de la galeria
        val seleccionar = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                guardarImagen(it)
            })

        bSubirImagen.setOnClickListener {
            seleccionar.launch("image/*")
        }

        bUserData.setOnClickListener {
            // Obtenemos el UUID desde el registro de usuario en firebase
            val id_user = firebaseAuth.currentUser?.uid
            val nombre = etRegistroNombre.text.toString()
            val apellido = etRegistroApellido.text.toString()
            val ubicacion = etRegistroUbi.text.toString()
            val descripcion = etRegistroDescr.text.toString()
            val telefono = etRegistroPhone.text.toString()

            // Construimos el JSONObject
            val jsonParams = JSONObject()
            jsonParams.put("id_user", id_user)
            jsonParams.put("nombre", nombre)
            jsonParams.put("apellido", apellido)
            jsonParams.put("ubicacion", ubicacion)
            jsonParams.put("descripcion", descripcion)
            jsonParams.put("telefono", telefono)

            // URL del endpoint
            val url = "https://www.arucc.lat/appMobile/profiles/index.php"

            // Crear una solicitud PUT usando JsonObjectRequest
            val request = object : JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonParams,
                Response.Listener { response ->
                    // Manejar la respuesta exitosa
                    Toast.makeText(this, "Solicitud exitosa", Toast.LENGTH_SHORT).show()

                    // Si fue exitoso lo mandamos al homepage
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener { error ->
                    // Manejar errores de la solicitud
                    Toast.makeText(this, "Error en la solicitud: $error", Toast.LENGTH_SHORT).show()
                }
            ) {

            }

            // Agregar la solicitud a la cola de Volley
            Volley.newRequestQueue(this).add(request)

            // El else debe ir aquí, dentro del bloque del clic del botón
        }
    }

    private fun guardarImagen(it: Uri?) {
        val id_user = firebaseAuth.currentUser?.uid
        // Esto es para guardar la imagen en tal ruta con tal nombre el archivo
        val file = storage.getReference().child("images/$id_user/" +"foto")

        if (it != null) {
            file.putFile(it).addOnCompleteListener {
                //se ejecutra cuando la carga/subida del archivo ha terminado
                file.downloadUrl.addOnCompleteListener { uri ->
                    // se ejecuta cuando la URL de la imagen esta disponible
                    val url: String = uri.result.toString()
                }
            }
        }
    }

}
