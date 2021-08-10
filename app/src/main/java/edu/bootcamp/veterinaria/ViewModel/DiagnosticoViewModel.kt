package edu.bootcamp.veterinaria.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.bootcamp.veterinaria.DAO.DBHelper
import edu.bootcamp.veterinaria.Model.Diagnostico

class DiagnosticoViewModel: ViewModel() {

    fun agregarDiagnostico(causas: String, medicamentos: String, context: Context): Int
    {
        val db: DBHelper = DBHelper(context,null)

        val idDiagnostico: Int = obtenerDiagnosticos(context).size + 1

        val diagnostico: Diagnostico = Diagnostico(idDiagnostico,causas,medicamentos)
        db.agregarDiagnostico(diagnostico)
        return idDiagnostico
    }

    fun obtenerDiagnostico(idDiagnostico: Int, context: Context): Diagnostico
    {
        val db:DBHelper = DBHelper(context,null)
        return db.obtenerDiagnostico(idDiagnostico)
    }

    fun obtenerDiagnosticos(context: Context): ArrayList<Diagnostico>
    {
        val db:DBHelper = DBHelper(context,null)
        return db.obtenerDiagnosticos()
    }
}