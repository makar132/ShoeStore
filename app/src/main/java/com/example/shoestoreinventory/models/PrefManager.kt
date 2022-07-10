package com.example.shoestoreinventory.models

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context?) {

    // Shared pref mode
    val PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = "SharedPreferences"

    private val LOGIN = "login_status"

    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()
    fun setLogin(loginStatus: Boolean) {
        editor?.putBoolean(LOGIN,loginStatus)
        editor?.commit()
    }
    fun Logedin(): Boolean? {
        return pref?.getBoolean(LOGIN, false)
    }
    fun setonboarding(status: Boolean) {
        editor?.putBoolean("onboarding",status)
        editor?.commit()
    }

    fun onboarding(): Boolean? {
        return pref?.getBoolean("onboarding", false)
    }
    fun logout() {
        editor?.clear()
        editor?.commit()
    }

}