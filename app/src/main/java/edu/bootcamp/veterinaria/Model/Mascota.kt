package edu.bootcamp.veterinaria.Model

import java.io.Serializable

data class Mascota(
    var idMascota: Int=0,
    val nombre: String,
    val tipo: String,
    val raza: String,
    val edad: Int,
    val estado: String,
    val veterinario: String,
    val fechaAtencion: String,
    val causaAtencion: String,
    val idDiagnostico: Int=0) : Serializable