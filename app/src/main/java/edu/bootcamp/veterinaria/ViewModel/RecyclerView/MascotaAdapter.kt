package edu.bootcamp.veterinaria.ViewModel.RecyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.bootcamp.veterinaria.Model.Mascota
import edu.bootcamp.veterinaria.R
import edu.bootcamp.veterinaria.View.DetallesMascotaActivity
import edu.bootcamp.veterinaria.View.MainActivity

class MascotaAdapter(val lista: ArrayList<Mascota>, mainAct: MainActivity) :
    RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {
    val mainActivity: MainActivity = mainAct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_mascotas,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_rv_nombre_mascota.text = lista[position].nombre
        holder.txt_rv_tipo_mascota.text = lista[position].tipo
        holder.txt_rv_fecha_atencion.text = lista[position].fechaAtencion
        holder.txt_rv_veterinario.text = lista[position].veterinario
        holder.txt_rv_estado.text = lista[position].estado

        holder.btn_ver_detalles.setOnClickListener(View.OnClickListener {
            val intentDetallesMascota: Intent = Intent(it.context,DetallesMascotaActivity::class.java)
            intentDetallesMascota.putExtra("mascota",lista[position])
            mainActivity.startActivityForResult(intentDetallesMascota,200)
        })
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txt_rv_nombre_mascota: TextView
        var txt_rv_tipo_mascota: TextView
        var txt_rv_fecha_atencion: TextView
        var txt_rv_veterinario: TextView
        var txt_rv_estado: TextView
        var btn_ver_detalles: Button


        init {
            txt_rv_nombre_mascota = view.findViewById(R.id.txt_rv_nombre_mascota)
            txt_rv_tipo_mascota = view.findViewById(R.id.txt_rv_tipo_mascota)
            txt_rv_fecha_atencion = view.findViewById(R.id.txt_rv_fecha_atencion)
            txt_rv_veterinario = view.findViewById(R.id.txt_rv_veterinario)
            txt_rv_estado = view.findViewById(R.id.txt_rv_estado)
            btn_ver_detalles = view.findViewById(R.id.btn_ver_detalles)
        }
    }
}