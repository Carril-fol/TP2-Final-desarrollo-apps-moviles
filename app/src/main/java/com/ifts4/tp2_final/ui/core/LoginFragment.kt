package com.ifts4.tp2_final.ui.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.gson.Gson

import com.ifts4.tp2_final.FragmentActivity
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentLoginBinding
import com.ifts4.tp2_final.user.viewmodel.UserViewModel

class LoginFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentLoginBinding

    // UserViewModel
    private val userViewModel by viewModels<UserViewModel>()

    // Gson
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding inflate
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Buttons
        val loginBtn = binding.loginBtn
        loginBtn.setOnClickListener {

            // Binding
            with(binding) {

                // Fields
                val emailField = editTextEmail.text.toString()
                val passwordField = editTextPassword.text.toString()

                if (isFieldsNotEmpty(emailField, passwordField)) {
                    if (isUserExists(emailField, passwordField)) {
                        saveDniUserSharedPreferences(emailField, passwordField)
                        goToFragmentActivity()
                    } else {
                        Toast.makeText(requireContext(), "Credenciales invalidas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Credenciales invalidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    // Funcs
    private fun isFieldsNotEmpty(emailUser: String, passwordUser: String): Boolean {
        return emailUser.isNotEmpty() && passwordUser.isNotEmpty()
    }

    private fun goToFragmentActivity() {
        val fragmentActivityIntent = Intent(requireActivity(), FragmentActivity::class.java)
        startActivity(fragmentActivityIntent)
        requireActivity().finish()
    }

    private fun isUserExists(emailUser: String, passwordUser: String): Boolean {
        val data = userViewModel.selectUserFilterByEmailAndPassword(emailUser, passwordUser)
        return if (data == null) {
            false
        } else {
            return (emailUser == data.email) && (passwordUser == data.password)
        }
    }

    private fun saveDniUserSharedPreferences(emailUser: String, passwordUser: String) {
        val data = userViewModel.selectUserFilterByEmailAndPassword(emailUser, passwordUser)

        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)
        val edit = preferences.edit()

        val userSerializer = UserSerializer(data.dni)
        val userToJsonFormat = gson.toJson(userSerializer)

        edit.putString("UserDni", userToJsonFormat)
        edit.apply()

    }

}