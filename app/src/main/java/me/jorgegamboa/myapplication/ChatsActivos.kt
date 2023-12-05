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
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject

class ChatsActivos : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: ChatsActivosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats_activos)

        // Declaramos recyclerview y el adapter
        val recyclerView : RecyclerView = findViewById(R.id.rvChatsActivos)
        adapter = ChatsActivosAdapter()
        // Declaramos el layout del recyclerview
        val linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        val id_user = firebaseAuth.currentUser?.uid

        // Primero generamos una peticion GET para obtener las conversaciones
        val sender = firebaseAuth.currentUser?.uid.toString()
        val requestQueueChats = Volley.newRequestQueue(this)
        val url: String = "https://www.arucc.lat/appMobile/chats/index.php?id_user=$id_user"
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
        requestQueueChats.add(request)
    }

    private fun procesarRespuesta(response: JSONObject) {
        val conversationsArray = response.getJSONArray("Conversaciones")
        val conversationList = mutableListOf<ChatData>()

        for (i in 0 until conversationsArray.length()) {
            val conversationObj = conversationsArray.getJSONObject(i)

            val id_conv = conversationObj.getInt("id_conv")
            val id_user2 = conversationObj.getString("id_user")
            val nombre = conversationObj.getString("nombre")
            val apellido = conversationObj.getString("apellido")
            val url_imagen = conversationObj.getString("url_imagen")
            val telefono = conversationObj.getString("telefono")
            val oficio = conversationObj.getString("oficio")

            // Create an instance of Conversation and add it to the list
            val ChatData = ChatData(id_conv, id_user2, nombre, apellido, url_imagen, telefono, oficio)
            conversationList.add(ChatData)
            Log.d("iduser", id_user2)
        }

        // Log the conversation data
        // Log the conversation data
        for (chatData in conversationList) {
            Log.i("ChatsActivos1", chatData.id_conv.toString())
            Log.i("ChatsActivos2", chatData.id_user2)
            Log.i("ChatsActivos3", chatData.nombre)
            Log.i("ChatsActivos4", chatData.apellido)
            Log.i("ChatsActivos5", chatData.url_imagen)
            Log.i("ChatsActivos6", chatData.telefono)
            Log.i("ChatsActivos7", chatData.oficio)
            Log.i("ChatsActivos8", "-----------------------")
        }

        adapter.setData(conversationList)


    }
}