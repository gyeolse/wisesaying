import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WiseSayingDataStore(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "wise_saying_settings")

    // Push 알림 설정 값 불러오기
    val pushNotificationEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PUSH_NOTIFICATION_KEY] ?: false  // 기본값은 false로 설정
        }

    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        val PUSH_NOTIFICATION_KEY = booleanPreferencesKey("push_notifications")
    }

    suspend fun saveThemePreference(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDarkMode
        }
    }

    fun getThemePreference(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }
    }

    suspend fun savePushNotificationPreference(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PUSH_NOTIFICATION_KEY] = isEnabled
        }
    }

    fun getPushNotificationPreference(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PUSH_NOTIFICATION_KEY] ?: false
        }
    }
}