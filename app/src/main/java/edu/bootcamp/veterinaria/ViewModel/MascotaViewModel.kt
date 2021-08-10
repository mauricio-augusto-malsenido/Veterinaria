package edu.bootcamp.veterinaria.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.bootcamp.veterinaria.DAO.DBHelper
import edu.bootcamp.veterinaria.Model.Mascota
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MascotaViewModel: ViewModel() {

    fun registrarMascota(nombre: String, tipo: String, raza: String, edad: Int, causaAtencion: String, veterinario: String, context: Context): Boolean
    {
        val db:DBHelper = DBHelper(context,null)

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val fechaAtencion = sdf.format(Date())

        val mascota: Mascota = Mascota(0,nombre,tipo,raza,edad,"Asignada",veterinario,fechaAtencion,causaAtencion,0)
        return db.agregarMascota(mascota)
    }

    fun modificarMascota(idMascota: Int, estado: String, idDiagnostico: Int, context: Context): Boolean
    {
        val db:DBHelper = DBHelper(context,null)
        return db.modificarMascota(idMascota,estado,idDiagnostico)
    }

    fun obtenerMascotas(context: Context): ArrayList<Mascota>
    {
        val db:DBHelper = DBHelper(context,null)
        return db.obtenerMascotas()
    }

    fun obtenerMascotasPorVeterinario(veterinario: String, context: Context): ArrayList<Mascota>
    {
        val db:DBHelper = DBHelper(context,null)
        return db.obtenerMascotasPorVeterinario(veterinario)
    }
}