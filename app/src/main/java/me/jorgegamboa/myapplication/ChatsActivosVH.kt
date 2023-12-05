package me.jorgegamboa.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatsActivosVH(itemView: View)  : RecyclerView.ViewHolder(itemView) {

    lateinit var tvNombreProfesional : TextView
    lateinit var tvApellidoProfesional : TextView
    val fila = itemView

    fun bind(chats: ChatData) {
        // Asignamos los valores a las variables previamente declaradas en el constructor
        tvNombreProfesional = itemView.findViewById(R.id.tvNombreProfesional)
        tvApellidoProfesional = itemView.findViewById(R.id.tvApellidoProfesional)

        tvNombreProfesional.text = chats.nombre
        tvApellidoProfesional.text= chats.apellido
    }
}