package edu.bootcamp.veterinaria.View

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import edu.bootcamp.veterinaria.Model.Diagnostico
import edu.bootcamp.veterinaria.Model.Mascota
import edu.bootcamp.veterinaria.R
import edu.bootcamp.veterinaria.ViewModel.DiagnosticoViewModel
import edu.bootcamp.veterinaria.ViewModel.MascotaViewModel

class DetallesMascotaActivity : AppCompatActivity() {
    lateinit var txt_id: TextView
    lateinit var txt_nombre: TextView
    lateinit var txt_tipo: TextView
    lateinit var txt_raza: TextView
    lateinit var txt_edad: TextView
    lateinit var txt_estado: TextView
    lateinit var txt_veterinario: TextView
    lateinit var txt_fecha_atencion: TextView
    lateinit var txt_causa_atencion: TextView
    lateinit var btn_agregar_diagnostico: Button
    lateinit var frame_diagnostico: FrameLayout

    lateinit var mascotaVM: MascotaViewModel
    lateinit var diagnosticoVM: DiagnosticoViewModel

    lateinit var mascota: Mascota
    lateinit var diagnostico: Diagnostico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_mascota)
        inicializarComponentes()

        mascotaVM = ViewModelProvider(this).get(MascotaViewModel::class.java)
        diagnosticoVM = ViewModelProvider(this).get(DiagnosticoViewModel::class.java)

        mascota = intent.getSerializableExtra("mascota") as Mascota

        txt_id.text = mascota.idMascota.toString()
        txt_nombre.text = mascota.nombre
        txt_tipo.text = mascota.tipo
        txt_raza.text = mascota.raza
        txt_edad.text = mascota.edad.toString()
        txt_estado.text = mascota.estado
        txt_veterinario.text = mascota.veterinario
        txt_fecha_atencion.text = mascota.fechaAtencion
        txt_causa_atencion.text = mascota.causaAtencion

        if (mascota.estado.equals("Atendida")) {
            diagnostico = diagnosticoVM.obtenerDiagnostico(mascota.idDiagnostico, this)
            mostrarDiagnostico()
        }

        btn_agregar_diagnostico.setOnClickListener(View.OnClickListener {
            val intentNuevoDiagnostico: Intent = Intent(this,NuevoDiagnosticoActivity::class.java)
            startActivityForResult(intentNuevoDiagnostico,300)
        })
    }

    private fun inicializarComponentes()
    {
        txt_id = findViewById(R.id.txt_id)
        txt_nombre = findViewById(R.id.txt_nombre)
        txt_tipo = findViewById(R.id.txt_tipo)
        txt_raza = findViewById(R.id.txt_raza)
        txt_edad = findViewById(R.id.txt_edad)
        txt_estado = findViewById(R.id.txt_estado)
        txt_veterinario = findViewById(R.id.txt_veterinario_asignado)
        txt_fecha_atencion = findViewById(R.id.txt_fecha_atencion)
        txt_causa_atencion = findViewById(R.id.txt_causa_atencion)
        btn_agregar_diagnostico = findViewById(R.id.btn_agregar_diagnostico)
        frame_diagnostico = findViewById(R.id.frame_diagnostico)
    }

    private fun mostrarDiagnostico()
    {
        val view = LayoutInflater.from(this).inflate(R.layout.detalles_diagnostico,null,false)

        val txt_causas: TextView = view.findViewById(R.id.txt_causas)
        val txt_medicamentos: TextView = view.findViewById(R.id.txt_medicamentos)

        txt_causas.text = diagnostico.causas
        txt_medicamentos.text = diagnostico.medicamentos

        btn_agregar_diagnostico.isVisible = false
        frame_diagnostico.addView(view)
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 300) {
            val idDiagnostico: Int = data!!.getIntExtra("idDiagnostico",0)
            diagnostico = diagnosticoVM.obtenerDiagnostico(idDiagnostico,this)
            mascotaVM.modificarMascota(mascota.idMascota,"Atendida",diagnostico.idDiagnostico,this)
            txt_estado.text = "Atendida"
            mostrarDiagnostico()
        }
    }
}