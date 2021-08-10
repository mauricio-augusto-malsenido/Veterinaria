package edu.bootcamp.veterinaria.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import edu.bootcamp.veterinaria.Model.Diagnostico
import edu.bootcamp.veterinaria.Model.Mascota
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "Veterinaria.db"
        private val DATABASE_VERSION = 1

        val TABLE_MASCOTA = "Mascota"
        val COLUMN_ID_MASCOTA = "idMascota"
        val COLUMN_NOMBRE = "nombre"
        val COLUMN_TIPO = "tipo"
        val COLUMN_RAZA = "raza"
        val COLUMN_EDAD = "edad"
        val COLUMN_ESTADO = "estado"
        val COLUMN_VETERINARIO = "veterinarioAsignado"
        val COLUMN_FECHA_ATENCION = "fechaAtencion"
        val COLUMN_CAUSA_ATENCION = "causaAtencion"

        val TABLE_DIAGNOSTICO = "Diagnostico"
        val COLUMN_ID_DIAGNOSTICO = "idDiagnostico"
        val COLUMN_CAUSAS = "causas"
        val COLUMN_MEDICAMENTOS = "medicamentos"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableMascota = ("CREATE TABLE $TABLE_MASCOTA (" +
                "$COLUMN_ID_MASCOTA INTEGER PRIMARY KEY, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_TIPO TEXT, " +
                "$COLUMN_RAZA TEXT, " +
                "$COLUMN_EDAD INTEGER, " +
                "$COLUMN_ESTADO TEXT, " +
                "$COLUMN_VETERINARIO TEXT, " +
                "$COLUMN_FECHA_ATENCION TEXT, " +
                "$COLUMN_CAUSA_ATENCION TEXT, " +
                "$COLUMN_ID_DIAGNOSTICO INTEGER, " +
                "FOREIGN KEY($COLUMN_ID_DIAGNOSTICO) REFERENCES $TABLE_DIAGNOSTICO($COLUMN_ID_DIAGNOSTICO))")

        val createTableDiagnostico = ("CREATE TABLE $TABLE_DIAGNOSTICO (" +
                "$COLUMN_ID_DIAGNOSTICO INTEGER PRIMARY KEY, " +
                "$COLUMN_CAUSAS TEXT, " +
                "$COLUMN_MEDICAMENTOS TEXT)")

        db?.execSQL(createTableDiagnostico)
        db?.execSQL(createTableMascota)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TALBE IF EXISTS " + TABLE_MASCOTA)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_DIAGNOSTICO)
        onCreate(db)
    }

    fun agregarMascota(mascota: Mascota): Boolean
    {
        try {
            val db = this.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_NOMBRE,mascota.nombre)
            values.put(COLUMN_TIPO,mascota.tipo)
            values.put(COLUMN_RAZA,mascota.raza)
            values.put(COLUMN_EDAD,mascota.edad)
            values.put(COLUMN_ESTADO,mascota.estado)
            values.put(COLUMN_VETERINARIO,mascota.veterinario)
            values.put(COLUMN_FECHA_ATENCION,mascota.fechaAtencion)
            values.put(COLUMN_CAUSA_ATENCION,mascota.causaAtencion)
            values.put(COLUMN_ID_DIAGNOSTICO,mascota.idDiagnostico)

            db.insert(TABLE_MASCOTA,null,values)
            return true
        }catch (e: Exception){
            Log.e("ERROR DATABASE:", e.message.toString())
            return false
        }
    }

    fun modificarMascota(idMascota: Int, estado: String, idDiagnostico: Int): Boolean
    {
        try {
            val db = this.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_ESTADO,estado)
            values.put(COLUMN_ID_DIAGNOSTICO,idDiagnostico)

            val whereclause = "$COLUMN_ID_MASCOTA=?"
            val whereargs = arrayOf(idMascota.toString())

            db.update(TABLE_MASCOTA,values,whereclause,whereargs)
            return true
        }catch (e: Exception){
            Log.e("ERROR DATABASE:", e.message.toString())
            return false
        }
    }

    fun obtenerMascotas(): ArrayList<Mascota>
    {
        val db = this.readableDatabase
        val listaMascotas: ArrayList<Mascota> = ArrayList()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        val query = "SELECT * FROM $TABLE_MASCOTA WHERE $COLUMN_FECHA_ATENCION LIKE '$currentDate'"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val idMascota = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MASCOTA))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val raza = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
                val edad = cursor.getInt(cursor.getColumnIndex(COLUMN_EDAD))
                val estado = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO))
                val veterinario = cursor.getString(cursor.getColumnIndex(COLUMN_VETERINARIO))
                val fechaAtencion = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_ATENCION))
                val causaAtencion = cursor.getString(cursor.getColumnIndex(COLUMN_CAUSA_ATENCION))
                val idDiagnostico = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DIAGNOSTICO))

                listaMascotas.add(Mascota(idMascota,nombre,tipo,raza,edad,estado,veterinario,fechaAtencion,causaAtencion,idDiagnostico))
            }while (cursor.moveToNext())
        }

        return listaMascotas
    }

    fun obtenerMascotasPorVeterinario(vet: String): ArrayList<Mascota>
    {
        val db = this.readableDatabase
        val listaMascotas: ArrayList<Mascota> = ArrayList()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        val query = "SELECT * FROM $TABLE_MASCOTA WHERE $COLUMN_FECHA_ATENCION LIKE '$currentDate' AND $COLUMN_VETERINARIO LIKE '$vet'"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val idMascota = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MASCOTA))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val raza = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
                val edad = cursor.getInt(cursor.getColumnIndex(COLUMN_EDAD))
                val estado = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO))
                val veterinario = cursor.getString(cursor.getColumnIndex(COLUMN_VETERINARIO))
                val fechaAtencion = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_ATENCION))
                val causaAtencion = cursor.getString(cursor.getColumnIndex(COLUMN_CAUSA_ATENCION))
                val idDiagnostico = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DIAGNOSTICO))

                listaMascotas.add(Mascota(idMascota,nombre,tipo,raza,edad,estado,veterinario,fechaAtencion,causaAtencion,idDiagnostico))
            }while (cursor.moveToNext())
        }

        return listaMascotas
    }

    fun agregarDiagnostico(diagnostico: Diagnostico): Boolean
    {
        try {
            val db = this.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_ID_DIAGNOSTICO,diagnostico.idDiagnostico)
            values.put(COLUMN_CAUSAS,diagnostico.causas)
            values.put(COLUMN_MEDICAMENTOS,diagnostico.medicamentos)

            db.insert(TABLE_DIAGNOSTICO,null,values)
            return true
        }catch (e: Exception){
            Log.e("ERROR DATABASE:", e.message.toString())
            return false
        }
    }

    fun obtenerDiagnostico(idDiag: Int): Diagnostico
    {
        val db = this.readableDatabase
        var diagnostico: Diagnostico = Diagnostico(0,"","")

        val query = "SELECT * FROM $TABLE_DIAGNOSTICO WHERE $COLUMN_ID_DIAGNOSTICO = $idDiag"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst())
        {
            val idDiagnostico = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DIAGNOSTICO))
            val causas = cursor.getString(cursor.getColumnIndex(COLUMN_CAUSAS))
            val medicamentos = cursor.getString(cursor.getColumnIndex(COLUMN_MEDICAMENTOS))

            diagnostico = Diagnostico(idDiagnostico,causas,medicamentos)
        }


        return diagnostico
    }

    fun obtenerDiagnosticos(): ArrayList<Diagnostico>
    {
        val db = this.readableDatabase
        val listaDiagnostico: ArrayList<Diagnostico> = ArrayList()

        val query = "SELECT * FROM $TABLE_DIAGNOSTICO"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val idDiagnostico = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DIAGNOSTICO))
                val causas = cursor.getString(cursor.getColumnIndex(COLUMN_CAUSAS))
                val medicamentos = cursor.getString(cursor.getColumnIndex(COLUMN_MEDICAMENTOS))

                listaDiagnostico.add(Diagnostico(idDiagnostico,causas,medicamentos))
            }while (cursor.moveToNext())
        }

        return listaDiagnostico
    }
}