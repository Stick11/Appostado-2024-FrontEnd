package com.proyecto.cuatro.appostados.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Validator {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Requisitos: Al menos 6 caracteres, una mayúscula, una minúscula, un número y un carácter especial.
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }
    fun isAge18OrOver(birthDate: Date): Boolean {
        val today = Calendar.getInstance()
        val birthDateCalendar = Calendar.getInstance()
        birthDateCalendar.time = birthDate // Directamente establecemos birthDate en el Calendar

        var age = today.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR)

        // Ajustamos el cálculo de la edad si el día de nacimiento de este año aún no ha pasado
        if (today.get(Calendar.DAY_OF_YEAR) < birthDateCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age >= 18
    }
    // Agregar aquí los métodos de validación necesarios
}