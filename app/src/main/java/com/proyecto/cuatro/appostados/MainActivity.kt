package com.proyecto.cuatro.appostados
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.Context
import com.proyecto.cuatro.appostados.HomeAdmin
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService
import com.proyecto.cuatro.appostados.databinding.ActivityMainBinding
import com.proyecto.cuatro.appostados.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming LoginService now checks SharedPreferences
        val masterService = MasterService()
        val sharedPreferences = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val loginService = LoginServiceSingleton.getInstance(masterService, sharedPreferences)

        if (loginService.isLoggedIn) {
            // User is logged in, decide which home screen to show based on the user role
            val user = loginService.getUserSistema()
            when (user?.rolUser) {
                1 -> startActivity(Intent(this, HomeAdmin::class.java))
                // For other roles, add corresponding cases
                else -> startActivity(Intent(this, LoginActivity::class.java)) // Fallback to login if role not handled
            }
        } else {
            // Not logged in, redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Close MainActivity to prevent returning here with the back button
    }
}
