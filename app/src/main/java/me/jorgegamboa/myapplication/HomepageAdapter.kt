package me.jorgegamboa.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomepageAdapter(private val context: Context): RecyclerView.Adapter<HomepageVH>() {
    private var datos: ArrayList<TrabajadorRandomData> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageVH {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.rvpopulares_fila, parent, false)
        return HomepageVH(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: HomepageVH, position: Int) {
        val trabajador = datos[position]
        holder.bind(trabajador)
    }

    fun actualizarDatos(trabajadores: java.util.ArrayList<TrabajadorRandomData>) {
        datos.clear()
        datos.addAll(trabajadores)  // Cambiado de 'nuevosDatos' a 'trabajadores'
        notifyDataSetChanged()
    }

}