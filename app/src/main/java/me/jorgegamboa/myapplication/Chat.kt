package me.jorgegamboa.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject

class Chat : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: ChatAdapter
    private var id_conv: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firebaseAuth = FirebaseAuth.getInstance()

        // Recyclerview del chat
        val rvChat : RecyclerView = findViewById(R.id.rvChatsActivos)
        adapter = ChatAdapter()
        rvChat.adapter = adapter
        rvChat.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

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

        // Obtener los mensajes existentes en la conversacion

        // Peticion get para obtener los mensajes
        val requestQueueMensajes = Volley.newRequestQueue(this)
        val requestMensaje: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                procesarMensajes(response)
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
            }, { error ->
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueueMensajes.add(request)

        // Declaramos boton y funcion para enviar mensaje
        val bSend : Button = findViewById(R.id.bSend)
        bSend.setOnClickListener {
            enviarMensaje()
        }
    }

    private fun procesarMensajes(response: JSONObject?) {
        
    }

    private fun procesarRespuesta(response: JSONObject) {
        id_conv = response.getInt("id_conv")
        Log.i("valores3 before",id_conv.toString())
    }

    private fun enviarMensaje(){

        val etMensaje: EditText = findViewById(R.id.etMensaje)

        val mensaje: MensajeData = MensajeData()
        mensaje.mensaje = etMensaje.text.toString().trim()

        //Checamos el mensaje no este vacio en el edittext
        if (!mensaje.mensaje.equals("")) {
            mensaje.id_sender = firebaseAuth.currentUser?.uid.toString()

            // Creamos el objeto Json a mandar
            val jsonParams = JSONObject()
            jsonParams.put("mensaje", mensaje.mensaje)
            jsonParams.put("id_sender", mensaje.id_sender)
            jsonParams.put("id_conv", id_conv)

            Log.i("valores",mensaje.mensaje)
            Log.i("valores2",mensaje.id_sender)
            Log.i("valores3",mensaje.id_trabajador)

            // Configurar la solicitud POST con Volley
            val requestQueue2 = Volley.newRequestQueue(this)
            val url: String = "https://www.arucc.lat/appMobile/chats/index.php"
            val request = object : JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonParams,
                Response.Listener { response ->
                    // Manejar la respuesta exitosa
                    Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    // Manejar errores de la solicitud
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

                }
            ) {

            }

            // Agregar la solicitud a la cola de Volley
            requestQueue2.add(request)
        }
            // Reseteamos el campo de escribir al mandar ultimo mensaje
            etMensaje.setText("")
        }
}


