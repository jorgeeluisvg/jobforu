package me.jorgegamboa.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //var ivFilaIcono : ImageView
    val tvNombrePersona: TextView = itemView.findViewById(R.id.tvNombrePersonaRandom)
    val tvProfesion: TextView = itemView.findViewById(R.id.tvProfesionRandom)
    val tvApellidoPersona: TextView = itemView.findViewById(R.id.tvApellidoPersona)
    val fila = itemView
    // Función bind para asignar datos a las vistas
    fun bind(trabajador: Trabajador) {
        tvNombrePersona.text = trabajador.nombre
        tvApellidoPersona.text = trabajador.apellido
        tvProfesion.text = trabajador.oficio


        // Asigna otros datos a las vistas según tus necesidades
    }
}
