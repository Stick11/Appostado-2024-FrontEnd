package com.proyecto.cuatro.appostados

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.proyecto.cuatro.appostados.data.model.UserRegistration
import com.proyecto.cuatro.appostados.data.services.UserRegisterService
import com.proyecto.cuatro.appostados.databinding.ActivityRegisterBinding
import com.proyecto.cuatro.appostados.ui.login.LoginActivity
import com.proyecto.cuatro.appostados.ui.registro.UserRegistrationViewModel
import com.proyecto.cuatro.appostados.ui.registro.UserRegistrationViewModelFactory
import com.proyecto.cuatro.appostados.util.Validator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    // Google SignIn Client
    private lateinit var googleSignInClient: GoogleSignInClient

    private val userRegisterService = UserRegisterService()

    private val viewModel: UserRegistrationViewModel by lazy {
        ViewModelProvider(this, UserRegistrationViewModelFactory(userRegisterService)).get(UserRegistrationViewModel::class.java)
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.profileImageView.setImageURI(uri)
            // Adjust the height of the ImageView (e.g., set it to 200 pixels)
            setImageHeight(500)
            // Save the URI of the selected image
            imageUriString = uri.toString()
        }
    }


    private var imageUriString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureGoogleSignIn()
        setListeners()

    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Autorellenar la información en los campos de texto
            binding.nameEditText.setText(account?.givenName ?: "")
            binding.lastName1EditText.setText(account?.familyName ?: "")
            binding.emailEditText.setText(account?.email ?: "")
            // Deshabilitar los campos para que no sean editables si se llenaron con datos de Google
            binding.emailEditText.isEnabled = false
            // - Navegar a otra actividad
        } catch (e: ApiException) {
            Toast.makeText(this, "Error al iniciar sesión con Google: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        // Establece el OnClickListener para el campo de fecha de nacimiento
        binding.birthdateEditText.setOnClickListener { showDatePickerDialog() }
        // Listener para el ImageView para abrir la galería
        binding.profileImageView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        // Establecer OnClickListener para el botón de registro
        binding.registerButton.setOnClickListener { registerUser() }
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePasswordConfirmation()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePasswordConfirmation()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        // Observar el estado del registro desde el ViewModel
        viewModel.registrationStatus.observe(this) { response ->
            if (response.success) {
                // Manejar el registro exitoso
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            } else {
                // Mostrar mensaje de error al usuario
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        }

        // Observar la redirección al LoginActivity después de un registro exitoso
        viewModel.redirectToLogin.observe(this) { redirectToLogin ->
            if (redirectToLogin) {
                // Si es necesario, aquí puedes agregar cualquier dato adicional que desees pasar a la LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                // Finalizar esta actividad para que el usuario no pueda regresar aquí presionando el botón de retroceso
                finish()
            }
        }
    }

    private fun showDatePickerDialog() {
        // Obtiene la fecha actual para inicializar el DatePicker
        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)
        val currentMonth = c.get(Calendar.MONTH)
        val currentDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
            // Formatea y muestra la fecha seleccionada
            val selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
            binding.birthdateEditText.setText(selectedDate)
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.show()
// Si es posible, muestra el selector de años primero
        try {
            datePickerDialog.datePicker.touchables[0].performClick()
        } catch (e: Exception) {
            // Si falla el intento de mostrar el selector de años primero, ignóralo
            // Esto puede fallar dependiendo de la implementación del DatePickerDialog en el dispositivo
        }

    }


    private fun registerUser() {
        // Obtener valores de los campos de contraseña y confirmación de contraseña
        val passwordInput = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        // Verificar si las contraseñas coinciden
        if (passwordInput != confirmPassword) {
            // Mostrar mensaje de error si las contraseñas no coinciden
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            return
        }
        val name = binding.nameEditText.text.toString().trim()
        val lastName1 = binding.lastName1EditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = passwordInput
        val birthDateString = binding.birthdateEditText.text.toString()

        // Validar que ningún campo esté vacío
        if (name.isEmpty() || lastName1.isEmpty()  || email.isEmpty() || password.isEmpty() || birthDateString.isEmpty()) {
            Toast.makeText(this, "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Otras validaciones como formato de email, fortaleza de la contraseña, etc.
        if (!Validator.isValidEmail(email)) {
            Toast.makeText(this, "Por favor, introduce un email válido.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Validator.isValidPassword(password)) {
            Toast.makeText(this, "La contraseña no cumple con los requisitos mínimos.", Toast.LENGTH_SHORT).show()
            return
        }

        val birthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(birthDateString)
        birthDate?.let {
            if (!Validator.isAge18OrOver(it)) {
                Toast.makeText(this, "Debes tener al menos 18 años para registrarte.", Toast.LENGTH_SHORT).show()
                return
            }

            // Obtener el timestamp de la fecha de nacimiento
            val birthDateTimestamp = birthDate.time // Convertir la fecha de nacimiento a un timestamp en milisegundos

            // Crear el objeto UserRegistration con todos los datos necesarios
            val userRegistration = UserRegistration(
                name = name,
                lastNames = lastName1,
                birthDate = birthDateTimestamp,
                foto = imageUriString,
                email = email,
                password = password
            )

            // Llamar al método del ViewModel para registrar al usuario
            viewModel.registerUser(userRegistration)
        } ?: run {
            Toast.makeText(this, "Formato de fecha de nacimiento inválido.", Toast.LENGTH_SHORT).show()
            return
        }
    }

    // Inside the activity or fragment where you handle image selection
    private fun setImageHeight(newHeightPx: Int) {
        val layoutParams = binding.profileImageView.layoutParams
        layoutParams.height = newHeightPx
        binding.profileImageView.layoutParams = layoutParams
    }
    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
    // Función para validar la coincidencia de contraseñas
    fun validatePasswordConfirmation() {
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (password == confirmPassword) {
            // Si las contraseñas coinciden, puedes cambiar el color del borde del EditText a su estado original
            binding.confirmPasswordLayout.error = null
        } else {
            // Si las contraseñas no coinciden, puedes cambiar el color del borde del EditText a rojo y mostrar un mensaje de error
            binding.confirmPasswordLayout.error = "Las contraseñas no coinciden"
        }
    }

}
