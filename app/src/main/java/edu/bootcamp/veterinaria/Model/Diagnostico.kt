package edu.bootcamp.veterinaria.Model

import java.io.Serializable

data class Diagnostico(val idDiagnostico: Int=0,val causas: String,val medicamentos: String): Serializable