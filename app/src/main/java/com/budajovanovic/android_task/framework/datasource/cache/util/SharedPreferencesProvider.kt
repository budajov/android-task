package com.budajovanovic.android_task.framework.datasource.cache.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesProvider
@Inject
constructor(private val context: Context) {

    private var prefs: SharedPreferences? = null

    fun init() {
        prefs = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun clearPostValidityTime() {
        setPostValidityTime(0)
    }

    fun setPostValidityTime(value: Long) {
        prefs!!.edit()
            .putLong(VALIDITY_POST_TIME_KEY, value)
            .apply()
    }

    fun postValidityTime() : Long {
        return prefs!!.getLong(VALIDITY_POST_TIME_KEY, 0)
    }

    /**
     * Clears all data from shared preferences
     */
    fun clear() {
        prefs!!.edit().clear().apply()
    }

}