import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePreferences {

    private const val FILE_NAME = "secure_app_prefs"
    private const val TOKEN_KEY = "userToken"

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun storeUserToken(context: Context, token: String) {
        val prefs = getEncryptedSharedPreferences(context)
        with(prefs.edit()) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    fun clearToken(context: Context) {
        val prefs = getEncryptedSharedPreferences(context)
        with(prefs.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }
}
