package edu.bootcamp.veterinaria.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import edu.bootcamp.veterinaria.Model.Mascota
import edu.bootcamp.veterinaria.R
import edu.bootcamp.veterinaria.ViewModel.MascotaViewModel

class NuevaMascotaActivity : AppCompatActivity() {

    lateinit var et_nombre: EditText
    lateinit var rg_tipo: RadioGroup
    lateinit var et_raza: EditText
    lateinit var et_edad: EditText
    lateinit var et_causa_atencion: EditText
    lateinit var sp_veterinario_asigando: Spinner
    lateinit var btn_guardar_mascota: Button
    lateinit var btn_cancelar_registro: Button

    lateinit var mascotaVM: MascotaViewModel
    val veterinarios = arrayOf("Dr Horacio Cartes","Dr Elena Martinez","Dr Rodrigo Gonzalez","Dr Roberto Tapari","Dr Mariana Hurtado")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mascota)

        inicializarComponentes()
        inicializarSpVeterinarioAsignado()

        mascotaVM = ViewModelProvider(this).get(MascotaViewModel::class.java)

        var veterinarioSeleccionado: String = ""
        sp_veterinario_asigando.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                veterinarioSeleccionado = veterinarios[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        btn_guardar_mascota.setOnClickListener(View.OnClickListener {
            val selected: Int = rg_tipo!!.checkedRadioButtonId
            val rb_selected: RadioButton = findViewById(selected)
            val tipo: String = rb_selected!!.text.toString()

            val mascotasPorVeterinario: ArrayList<Mascota> = mascotaVM.obtenerMascotasPorVeterinario(veterinarioSeleccionado,this)

            if (mascotasPorVeterinario.size < 5)
            {
                if(mascotaVM.registrarMascota(et_nombre.text.toString(),tipo,et_raza.text.toString(),et_edad.text.toString().toInt(),et_causa_atencion.text.toString(),veterinarioSeleccionado,this))
                {
                    Toast.makeText(it.context, "Mascota Guardada!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }
                else
                    Toast.makeText(it.context,"Error al guardar la mascota", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(it.context,"Se llegó al límite de 5 mascotas para este veterinario", Toast.LENGTH_LONG).show()
        })

        btn_cancelar_registro.setOnClickListener(View.OnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        })
    }

    private fun inicializarComponentes()
    {
        et_nombre = findViewById(R.id.et_nombre)
        rg_tipo = findViewById(R.id.rg_tipo)
        et_raza = findViewById(R.id.et_raza)
        et_edad = findViewById(R.id.et_edad)
        et_causa_atencion = findViewById(R.id.et_causa_atencion)
        sp_veterinario_asigando = findViewById(R.id.sp_veterinario_asignado)
        btn_guardar_mascota = findViewById(R.id.btn_guardar_mascota)
        btn_cancelar_registro = findViewById(R.id.btn_cancelar_registro)
    }

    private fun inicializarSpVeterinarioAsignado()
    {
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,veterinarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_veterinario_asigando.adapter = adapter
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }
}