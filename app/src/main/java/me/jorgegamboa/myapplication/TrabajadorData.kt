package me.jorgegamboa.myapplication

import org.json.JSONObject

class Trabajador(
    val id_user: String,
    val nombre: String,
    val apellido: String,
    val ubicacion: String,
    val descripcion: String,
    val oficio: String,
    val url_imagen: String
) {
    // constructor que acepte un objeto JSON y
    // establezca sus propiedades en función de ese JSON:
    companion object {
        fun fromJson(json: JSONObject,valorRecibido : String): Trabajador {
            return Trabajador(
                json.optString("id_user", ""),
                json.optString("Nombre", ""),
                json.optString("Apellido", ""),
                json.optString("Ubicacion", ""),
                json.optString("Descripcion", ""),
                json.optString("Oficio", "$valorRecibido"),
                json.optString("url_imagen", "")
            )
        }
    }
}
