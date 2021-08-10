package edu.bootcamp.veterinaria.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.bootcamp.veterinaria.Model.Mascota
import edu.bootcamp.veterinaria.R
import edu.bootcamp.veterinaria.ViewModel.MascotaViewModel
import edu.bootcamp.veterinaria.ViewModel.RecyclerView.MascotaAdapter


class MainActivity : AppCompatActivity() {

    lateinit var sp_filtro_veterinario: Spinner
    lateinit var rv_mascotas: RecyclerView
    lateinit var fab_nueva_mascota: FloatingActionButton

    lateinit var mascotaVM: MascotaViewModel
    val veterinarios = arrayOf("Todos","Dr Horacio Cartes","Dr Elena Martinez","Dr Rodrigo Gonzalez","Dr Roberto Tapari","Dr Mariana Hurtado")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarComponentes()
        inicializarSpFiltroVeterinario()

        mascotaVM = ViewModelProvider(this).get(MascotaViewModel::class.java)

        rv_mascotas.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rv_mascotas.adapter = MascotaAdapter(mascotaVM.obtenerMascotas(this),this)

        sp_filtro_veterinario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtrar()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        fab_nueva_mascota.setOnClickListener(View.OnClickListener {
            if (mascotaVM.obtenerMascotas(this).size < 20)
            {
                val intentNuevaMascota: Intent = Intent(this,NuevaMascotaActivity::class.java)
                startActivityForResult(intentNuevaMascota,100)
            }
            else
            {
                Toast.makeText(this,"Se llegó al limite de 20 mascotas por día",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun inicializarComponentes()
    {
        sp_filtro_veterinario = findViewById(R.id.sp_filtro_veterinario)
        rv_mascotas = findViewById(R.id.rv_mascotas)
        fab_nueva_mascota = findViewById(R.id.fab_nueva_mascota)
    }

    private fun inicializarSpFiltroVeterinario()
    {
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,veterinarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_filtro_veterinario.adapter = adapter
    }

    private fun filtrar()
    {
        val mascotas: ArrayList<Mascota>
        val veterinario: String = sp_filtro_veterinario.selectedItem.toString()

        if (veterinario.contentEquals("Todos"))
        {
            mascotas = mascotaVM.obtenerMascotas(this)
        }
        else
        {
            mascotas = mascotaVM.obtenerMascotasPorVeterinario(veterinario,this)
        }
        rv_mascotas.adapter = MascotaAdapter(mascotas,this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100) {
            rv_mascotas.adapter = MascotaAdapter(mascotaVM.obtenerMascotas(this),this)
        }
        if(resultCode == Activity.RESULT_OK && requestCode == 200) {
            sp_filtro_veterinario.setSelection(0)
            rv_mascotas.adapter = MascotaAdapter(mascotaVM.obtenerMascotas(this),this)
        }
    }
}