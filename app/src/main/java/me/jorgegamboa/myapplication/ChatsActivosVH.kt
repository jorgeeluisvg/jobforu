package me.jorgegamboa.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatsActivosVH : RecyclerView.ViewHolder{
    lateinit var tvNombreProfesional : TextView
    lateinit var tvApellidoProfesional : TextView
    val fila = itemView

    // Declaramos constructor de la vista
    constructor(itemView: View) : super(itemView){
        // Asignamos los valores a las variables previamente declaradas en el constructor
        tvNombreProfesional = itemView.findViewById(R.id.tvNombreProfesional)
        tvApellidoProfesional = itemView.findViewById(R.id.tvApellidoProfesional)

    }
}