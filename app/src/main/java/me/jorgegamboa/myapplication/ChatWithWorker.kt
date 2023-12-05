package me.jorgegamboa.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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

class ChatWithWorker : AppCompatActivity() {
    private lateinit var adapter : ChatWithWorkerAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var id_usuario : String
    private lateinit var id_trabajador : String
    private lateinit var id_conv : String
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_with_worker)
        firebaseAuth = FirebaseAuth.getInstance()

        id_usuario = intent.getStringExtra("id_usuario").toString()
        id_trabajador = intent.getStringExtra("id_trabajador").toString()
        id_conv = intent.getStringExtra("id_conv").toString()
        Log.i("id_usuarioF", id_usuario.toString())
        Log.i("id_trabajadorF", id_trabajador.toString())
        Log.i("id_convF", id_conv.toString())

        // Recyclerview
        val rvChat : RecyclerView = findViewById(R.id.rvChatWithWorker)
        adapter = ChatWithWorkerAdapter()
        rvChat.adapter = adapter
        rvChat.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        // Edittext
        val etMensaje2 : EditText = findViewById(R.id.etMensaje2)

        // Boton
        val bSend2 : Button = findViewById(R.id.bSend2)

        // Llamada inicial a obtenerMensajes
        obtenerMensajes()

        bSend2.setOnClickListener {
            val mensaje: MensajeData = MensajeData()
            mensaje.mensaje = etMensaje2.text.toString().trim()

            //Checamos el mensaje no este vacío en el EditText
            if (!mensaje.mensaje.equals("")) {
                mensaje.id_sender = firebaseAuth.currentUser?.uid.toString()

                // Creamos el objeto Json a enviar
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
                        // Llamada a obtenerMensajes solo después de enviar un nuevo mensaje
                        obtenerMensajes()
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
            // Reseteamos el campo de escribir al mandar último mensaje
            etMensaje2.setText("")
        }

        // Programar la ejecución periódica cada 2 segundos
        handler.postDelayed(object : Runnable {
            override fun run() {
                obtenerMensajes()
                handler.postDelayed(this, 2000) // 2 segundos
            }
        }, 2000) // 2 segundos
    }

    private fun procesarRespuesta(response: JSONObject?) {
        if (response != null) {
            val mensajesArray = response.getJSONArray("Messages")
            val messageList = mutableListOf<MensajeData>()

            // Iterar a través de los mensajes
            for (i in 0 until mensajesArray.length()) {
                val mensajeObj = mensajesArray.getJSONObject(i)

                // Extraer la información del mensaje
                val mensaje = mensajeObj.getString("mensaje")
                val idSender = mensajeObj.getString("id_sender")
                val fecha = mensajeObj.getString("fecha")

                // Crear una instancia de Message y agregarla a la lista
                val mensajeData = MensajeData().apply {
                    this.id_trabajador = id_trabajador
                    this.id_sender = id_sender
                    this.mensaje = mensaje
                    Log.d("MensajeData", "id_trabajador: $id_trabajador, id_sender: $id_sender, mensaje: $mensaje")
                }
                messageList.add(mensajeData)
            }
            messageList.reverse()
            adapter.add(messageList)
            adapter.notifyDataSetChanged()
        }
    }

    fun obtenerMensajes() {
        // Peticion get para obtener los mensajes
        val requestQueue = Volley.newRequestQueue(this)
        val url: String =
            "https://www.arucc.lat/appMobile/chats/index.php?id_user_1=$id_usuario&id_user_2=$id_trabajador"
        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                procesarRespuesta(response)
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    override fun onDestroy() {
        // Detener la ejecución periódica al destruir la actividad
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}