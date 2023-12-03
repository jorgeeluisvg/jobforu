package me.jorgegamboa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SearchResults : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultsAdapter
    private lateinit var valorRecibido: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        // Declaramos recyclerview y el adapter
        val recyclerView : RecyclerView = findViewById(R.id.rvResultadoTrabajadores)
        adapter = SearchResultsAdapter(this)

        // Declaramos el layout del recyclerview
        val linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        consultarTrabajadores()
    }

    // Funcion para consultar trabajadores por el tipo oficio
    private fun consultarTrabajadores(){
        valorRecibido = intent.getStringExtra("oficio").toString()
        Log.i("vrecibido", valorRecibido.toString())
        val requestQueue = Volley.newRequestQueue(this)
        val url : String = "https://www.arucc.lat/appMobile/profiles/index.php?tag=$valorRecibido"
        val request : JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                ProcesarRespuesta(response)
                Toast.makeText(this,"Correcto",Toast.LENGTH_SHORT).show()
            }, { error ->
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    private fun ProcesarRespuesta(response: JSONObject?){
        if (response != null){
            val profilesArray: JSONArray = response.getJSONArray("Profiles")
            val trabajadores: ArrayList<Trabajador> = ArrayList()

            for (i in 0 until profilesArray.length()) {
                val profileObject: JSONObject = profilesArray.getJSONObject(i)
                val trabajador = Trabajador.fromJson(profileObject,valorRecibido)
                trabajadores.add(trabajador)

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