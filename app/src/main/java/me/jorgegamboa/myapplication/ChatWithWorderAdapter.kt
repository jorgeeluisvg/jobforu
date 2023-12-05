package me.jorgegamboa.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatWithWorderAdapter : RecyclerView.Adapter<ChatWithWorkerVH>{
    // Declaramos arraylist datos
    private lateinit var datos : ArrayList<MensajeData>

    constructor(){
        datos = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatWithWorkerVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.chat_fila,
            parent,
            false)

        // Aqui enviamos la vista de las filas al viewholder
        return ChatWithWorkerVH(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: ChatWithWorkerVH, position: Int) {
        // Hacemos relacion de los datos con los items de la fila
        val mensaje = datos.get(position)

        Log.i("Mensaje",mensaje.mensaje)

        holder.tvFechaHora.text = mensaje.id_sender
        holder.tvMensaje.text = mensaje.mensaje
    }

    // Funcion introducir objetos tipo Mensaje
    fun add(mensaje : List<MensajeData>){
        //introducir nuevo mensaje en el adapter
        datos.addAll(mensaje)
        this.notifyDataSetChanged()

    }

}