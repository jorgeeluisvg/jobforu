package me.jorgegamboa.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ChatWithWorker : AppCompatActivity() {
    private lateinit var adapter : ChatWithWorderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_with_worker)

        val id_usuario = intent.getStringExtra("id_usuario")
        val id_trabajador = intent.getStringExtra("id_trabajador")

        val rvChat : RecyclerView = findViewById(R.id.rvChatWithWorker)
        adapter = ChatWithWorderAdapter()
        rvChat.adapter = adapter
        rvChat.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

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
            }, { error ->
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)

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


                // Aquí puedes hacer algo con la lista de mensajes
                // Por ejemplo, mostrarla en el RecyclerView o almacenarla en una variable de clase
            }
            messageList.reverse()
            adapter.add(messageList)

        }
    }
}