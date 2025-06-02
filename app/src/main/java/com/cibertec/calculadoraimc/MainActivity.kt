package com.cibertec.calculadoraimc

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var editPeso: TextInputEditText
    private lateinit var editAltura: TextInputEditText
    private lateinit var txtResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editPeso = findViewById(R.id.editPeso)
        editAltura = findViewById(R.id.editAltura)
        txtResultado = findViewById(R.id.txtResultado)

        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar)

        btnCalcular.setOnClickListener { calcularIMC() }
        btnLimpiar.setOnClickListener { limpiarCampos() }
    }

    private fun calcularIMC() {
        val pesoTexto = editPeso.text.toString()
        val alturaTexto = editAltura.text.toString()

        if (pesoTexto.isBlank() || alturaTexto.isBlank()) {
            AlertDialog.Builder(this)
                .setTitle("Faltan datos")
                .setMessage("Por favor, ingrese los datos faltantes.")
                .setPositiveButton("OK", null)
                .show()
            return
        }

        val peso = pesoTexto.toFloat()
        var altura = alturaTexto.toFloat()

        if (altura > 3) altura /= 100

        val imc = peso / (altura * altura)
        val imcRedondeado = String.format("%.1f", imc).toFloat()
        val (categoria, color) = obtenerCategoria(imcRedondeado)

        val textoCompleto = "IMC: $imcRedondeado - $categoria"


        val spannable = SpannableString(textoCompleto)


        val start = textoCompleto.indexOf(categoria)
        val end = start + categoria.length


        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor(color)),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        txtResultado.text = spannable
    }

    private fun obtenerCategoria(imc: Float): Pair<String, String> {
        return when {
            imc < 18.5 -> "Bajo peso" to "#2196F3"
            imc <= 24.9 -> "Peso normal" to "#4CAF50"
            imc <= 29.9 -> "Sobrepeso" to "#FFEB3B"
            else -> "Obesidad" to "#F44336"
        }
    }

    private fun limpiarCampos() {
        editPeso.text?.clear()
        editAltura.text?.clear()
        txtResultado.text = ""
    }
}