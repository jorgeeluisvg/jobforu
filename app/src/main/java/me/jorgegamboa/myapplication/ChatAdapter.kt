package me.jorgegamboa.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class ChatAdapter: RecyclerView.Adapter<ChatVH> {
    // Declaramos arraylist datos
    private lateinit var datos : ArrayList<MensajeData>

    constructor(){
        datos = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.chat_fila,
            parent,
            false)

        // Aqui enviamos la vista de las filas al viewholder
        return ChatVH(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: ChatVH, position: Int) {
        // Hacemos relacion de los datos con los items de la fila
        val mensaje = datos.get(position)

        holder.tvFechaHora.text = mensaje.id_sender
        holder.tvMensaje.text = mensaje.mensaje
    }

    // Funcion introducir objetos tipo Mensaje
    fun add(mensaje : MensajeData){
        //introducir nuevo mensaje en el adapter
        datos.add(mensaje)
        this.notifyDataSetChanged()

    }

}