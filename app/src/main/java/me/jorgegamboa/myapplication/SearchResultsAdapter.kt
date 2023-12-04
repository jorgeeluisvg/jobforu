package me.jorgegamboa.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchResultsAdapter(private val context: Context) : RecyclerView.Adapter<SearchResultsVH>() {

    private var datos: ArrayList<Trabajador> = ArrayList()


    //METODOS IMPORTANTES

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsVH {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.rvsearchresults_fila, parent, false)
        return SearchResultsVH(view)
    }

    override fun onBindViewHolder(holder: SearchResultsVH, position: Int) {
        val trabajador = datos[position]
        holder.bind(trabajador)

        holder.fila.setOnClickListener {
            val intent = Intent(context,WorkerFullViewActivity::class.java)
            intent.putExtra("nombre",trabajador.nombre)
            intent.putExtra("descripcion",trabajador.descripcion)
            intent.putExtra("id_trabajador",trabajador.id_user)
            Log.i("descripcion",trabajador.descripcion)
            Log.i("id_trabajador",trabajador.id_user)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun actualizarDatos(trabajadores: ArrayList<Trabajador>) {
        datos.clear()
        datos.addAll(trabajadores)  // Cambiado de 'nuevosDatos' a 'trabajadores'
        notifyDataSetChanged()
    }
}
