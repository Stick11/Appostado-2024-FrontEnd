package com.proyecto.cuatro.appostados
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.Context
import android.widget.TextView
import com.proyecto.cuatro.appostados.HomeAdmin
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService
import com.proyecto.cuatro.appostados.databinding.ActivityLoginBinding
import com.proyecto.cuatro.appostados.databinding.ActivityMainBinding
import com.proyecto.cuatro.appostados.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val masterService = MasterService()
        val sharedPreferences = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val loginService = LoginServiceSingleton.getInstance(masterService, sharedPreferences)

        if (loginService.isLoggedIn) {
            val user = loginService.getUserSistema()
            when (user?.rolUser) {
                1 -> startActivity(Intent(this, HomeAdmin::class.java))
                else -> startActivity(Intent(this, LoginActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }



        finish()

    }

}
