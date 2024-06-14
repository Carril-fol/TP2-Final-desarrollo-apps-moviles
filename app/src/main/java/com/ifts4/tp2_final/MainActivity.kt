package com.ifts4.tp2_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.databinding.ActivityMainBinding
import com.ifts4.tp2_final.ui.core.LoginFragment
import com.ifts4.tp2_final.ui.core.RegisterFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Data in shared preferences
        val dataSharedPreferences = userDataInSharedPreferences()

        if (dataSharedPreferences) {
            goToFragmentActivity()
        }

        // Buttons
        val registerBtn = binding.registerSignUpBtn
        val loginBtn = binding.loginBtn

        // Click listener
        registerBtn.setOnClickListener {
            goToRegisterFragment()
        }

        loginBtn.setOnClickListener {
            goToLoginFragment()
        }

    }

    // Funcs
    private fun goToRegisterFragment() {
        val fragment = RegisterFragment()
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun goToLoginFragment() {
        val fragment = LoginFragment()
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun goToFragmentActivity() {
        val fragmentActivityIntent = Intent(this, FragmentActivity::class.java)
        startActivity(fragmentActivityIntent)
        finish()
    }

    private fun userDataInSharedPreferences(): Boolean {
        val preferences = getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val dataInSharedPreferences = preferences.getString("UserDni", null)
        return if (dataInSharedPreferences != null) {
            true
        } else {
            false
        }
    }

}