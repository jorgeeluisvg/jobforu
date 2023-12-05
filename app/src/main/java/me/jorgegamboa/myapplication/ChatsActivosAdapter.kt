package me.jorgegamboa.myapplication


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatsActivosAdapter(private val context: Context) : RecyclerView.Adapter<ChatsActivosVH>(){
    private lateinit var firebaseAuth: FirebaseAuth

    // Declaramos arraylist datos
    private var datos: ArrayList<ChatData> = ArrayList()

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
        val chats = datos[position]
        holder.bind(chats)

        // Carga de mensajes con la persona seleccionada
        holder.fila.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val id_usuario = auth.currentUser?.uid
            val intent = Intent(context,ChatWithWorker::class.java)
            intent.putExtra("id_usuario",id_usuario)
            intent.putExtra("id_trabajador",chats.id_user2)
            Log.i("id_usuario", id_usuario.toString())
            Log.i("id_trabajador", chats.id_user2.toString())
            context.startActivity(intent)
        }

    }
}