package me.jorgegamboa.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class Homepage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomepageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Declaramos recyclerview y el adapter
        val recyclerView : RecyclerView = findViewById(R.id.rvAleatorio)
        adapter = HomepageAdapter(this)
        // Declaramos el layout del recyclerview
        val linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        // Iconos
        val ivPerfil : ImageView = findViewById(R.id.ivPerfil)
        val ivChat : ImageView = findViewById(R.id.ivChat)

        // Edit texts
        val etBuscar: EditText = findViewById(R.id.etBuscar)

        etBuscar.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Realiza la acción que deseas aquí
                val oficio = etBuscar.text.toString()
                val intent = Intent(this, SearchResults::class.java)
                intent.putExtra("oficio",oficio)
                startActivity(intent)
                return@setOnKeyListener true
            }
            false
        }

        ivPerfil.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        ivChat.setOnClickListener{
            val intent = Intent(this, ChatsActivos::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        consultarTrabajadores()
    }

    private fun consultarTrabajadores() {
        val requestQueue = Volley.newRequestQueue(this)
        val url : String = "https://www.arucc.lat/appMobile/profiles/index.php"
        val request : JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                ProcesarRespuesta(response)
                Toast.makeText(this,"Correcto", Toast.LENGTH_SHORT).show()
            }, { error ->
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)

    }

    private fun ProcesarRespuesta(response: JSONObject?) {
        if (response != null){
            val profilesArray: JSONArray = response.getJSONArray("Profiles")
            val trabajadores: ArrayList<TrabajadorRandomData> = ArrayList()

            for (i in 0 until profilesArray.length()) {
                val profileObject: JSONObject = profilesArray.getJSONObject(i)
                val trabajadoresRandom = TrabajadorRandomData.fromJson(profileObject)
                trabajadores.add(trabajadoresRandom)

                // Imprimir en la consola para verificar
                Log.d("Perfil", trabajadores.toString())

                // Aquí puedes hacer lo que necesites con los datos
                // Por ejemplo, puedes almacenarlos en una lista, base de datos, etc.
                // También puedes mostrarlos en la interfaz de usuario, etc.
            }
            adapter.actualizarDatos(trabajadores)
        }
    }
}
