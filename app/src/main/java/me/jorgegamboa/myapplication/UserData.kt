package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject

class UserData : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_data)

        firebaseAuth = FirebaseAuth.getInstance()

        // EditTexts
        val etRegistroNombre: EditText = findViewById(R.id.etRegistroNombre)
        val etRegistroApellido: EditText = findViewById(R.id.etRegistroApellido)
        val etRegistroUbi: EditText = findViewById(R.id.etRegistroUbicacion)
        val etRegistroDescr: EditText = findViewById(R.id.etRegistroDescripcion)
        val etRegistroImagen: EditText = findViewById(R.id.etRegistroImagen)
        val etRegistroPhone: EditText = findViewById(R.id.etRegistroTelefono)
        val etRegistroOficio: EditText = findViewById(R.id.etRegistroOficio)


        // Boton
        val bUserData: Button = findViewById(R.id.bUserData)

        bUserData.setOnClickListener {
            // Obtenemos el UUID desde el registro de usuario en firebase
            val id_user = firebaseAuth.currentUser?.uid
            val nombre = etRegistroNombre.text.toString()
            val apellido = etRegistroApellido.text.toString()
            val ubicacion = etRegistroUbi.text.toString()
            val descripcion = etRegistroDescr.text.toString()
            val urlImagen = etRegistroImagen.text.toString()
            val telefono = etRegistroPhone.text.toString()
            val oficio = etRegistroOficio.text.toString()

            // Construimos el JSONObject
            val jsonParams = JSONObject()
            jsonParams.put("id_user", id_user)
            jsonParams.put("nombre", nombre)
            jsonParams.put("apellido", apellido)
            jsonParams.put("ubicacion", ubicacion)
            jsonParams.put("descripcion", descripcion)
            jsonParams.put("url_imagen", urlImagen)
            jsonParams.put("telefono", telefono)
            jsonParams.put("oficio", oficio)

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
}