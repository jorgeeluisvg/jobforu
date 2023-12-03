package me.jorgegamboa.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomepageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //var ivFilaIcono : ImageView
    val tvNombrePersonaRandom: TextView = itemView.findViewById(R.id.tvNombrePersonaRandom)
    val tvProfesionRandom: TextView = itemView.findViewById(R.id.tvProfesionRandom)
    val tvApellidoRandom: TextView = itemView.findViewById(R.id.tvApellidoRandom)

    // Función bind para asignar datos a las vistas
    fun bind(trabajador: TrabajadorRandomData) {
        tvNombrePersonaRandom.text = trabajador.nombre
        tvApellidoRandom.text = trabajador.apellido
        tvProfesionRandom.text = trabajador.oficio


        // Asigna otros datos a las vistas según tus necesidades
    }
}