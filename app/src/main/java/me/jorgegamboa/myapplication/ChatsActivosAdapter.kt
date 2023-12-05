package me.jorgegamboa.myapplication


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatsActivosAdapter: RecyclerView.Adapter<ChatsActivosVH>  {

    // Declaramos arraylist datos
    private lateinit var datos : ArrayList<ChatData>
    constructor(){
        datos = ArrayList()
    }

    fun setData(newData: List<ChatData>) {
        datos.clear()
        datos.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsActivosVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.chats_activos_fila,
            parent,
            false)

        // Aqui enviamos la vista de las filas al viewholder
        return ChatsActivosVH(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: ChatsActivosVH, position: Int) {
        val chats = datos.get(position)
        holder.tvNombreProfesional.text = chats.nombre
        holder.tvApellidoProfesional.text = chats.apellido
        
    }
}