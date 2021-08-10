package edu.bootcamp.veterinaria.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import edu.bootcamp.veterinaria.R
import edu.bootcamp.veterinaria.ViewModel.DiagnosticoViewModel

class NuevoDiagnosticoActivity : AppCompatActivity() {

    lateinit var et_causas: EditText
    lateinit var et_medicamentos: EditText
    lateinit var btn_guardar_diagnostico: Button
    lateinit var btn_cancelar_nuevo_diagnostico: Button

    lateinit var diagnosticoVM: DiagnosticoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_diagnostico)
        inicializarComponentes()

        diagnosticoVM = ViewModelProvider(this).get(DiagnosticoViewModel::class.java)

        btn_guardar_diagnostico.setOnClickListener(View.OnClickListener {
            val idDiagnostico: Int = diagnosticoVM.agregarDiagnostico(et_causas.text.toString(),et_medicamentos.text.toString(),it.context)
            Toast.makeText(it.context, "Diagnóstico Guardada!", Toast.LENGTH_SHORT).show()
            intent.putExtra("idDiagnostico",idDiagnostico)
            setResult(RESULT_OK,intent)
            finish()
        })

        btn_cancelar_nuevo_diagnostico.setOnClickListener(View.OnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        })
    }

    private fun inicializarComponentes()
    {
        et_causas = findViewById(R.id.et_causas)
        et_medicamentos = findViewById(R.id.et_medicamentos)
        btn_guardar_diagnostico = findViewById(R.id.btn_guardar_diagnostico)
        btn_cancelar_nuevo_diagnostico = findViewById(R.id.btn_cancelar_nuevo_diagnostico)
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }
}