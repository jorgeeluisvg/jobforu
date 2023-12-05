package me.jorgegamboa.myapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class HomepageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //var ivFilaIcono : ImageView
    val tvNombrePersonaRandom: TextView = itemView.findViewById(R.id.tvNombrePersonaRandom)
    val tvProfesionRandom: TextView = itemView.findViewById(R.id.tvProfesionRandom)
    val tvApellidoRandom: TextView = itemView.findViewById(R.id.tvApellidoRandom)
    val ivPopulares : ImageView = itemView.findViewById(R.id.ivPopulares)

    // Función bind para asignar datos a las vistas
    fun bind(trabajador: TrabajadorRandomData) {
        tvNombrePersonaRandom.text = trabajador.nombre
        tvApellidoRandom.text = trabajador.apellido
        tvProfesionRandom.text = trabajador.oficio


        var id_user = trabajador.id_user

        // Obtén la referencia al Storage
        val storageReference = FirebaseStorage.getInstance().reference

        // Obtén la referencia a la imagen utilizando el UID del usuario
        val imageReference = storageReference.child("images/$id_user/foto")

        // Descarga la URL de la imagen
        imageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // La URL de la imagen está disponible
                val imageUrl = task.result.toString()


                // Carga la imagen en el ImageView utilizando Picasso
                Picasso.get()
                    .load(imageUrl)
                    .rotate(270F)
                    .into(ivPopulares)  // 'imageView' es tu ImageView en el diseño XML
            }
        }
    }
}