package me.jorgegamboa.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatWithWorkerVH : RecyclerView.ViewHolder {

    lateinit var tvMensaje : TextView
    lateinit var tvFechaHora : TextView
    // Declaramos constructor de la vista
    constructor(itemView: View) : super(itemView){
        // Asignamos los valores a las variables previamente declaradas en el constructor
        tvMensaje = itemView.findViewById(R.id.tvMensaje)
        tvFechaHora = itemView.findViewById(R.id.tvFechaHora)

    }
}