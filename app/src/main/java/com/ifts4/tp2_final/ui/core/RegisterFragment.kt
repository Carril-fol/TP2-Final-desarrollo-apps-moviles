package com.ifts4.tp2_final.ui.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ifts4.tp2_final.data.entities.UserEntity

import com.ifts4.tp2_final.FragmentActivity
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentRegisterBinding
import com.ifts4.tp2_final.user.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentRegisterBinding

    // ViewModel
    private val userViewModel by viewModels<UserViewModel>()

    // Gson
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Button
        val saveDataBtn = binding.registerSaveData

        // Set on click
        saveDataBtn.setOnClickListener {

            // Binding
            with(binding) {

                // Fields
                val firstNameFieldUser = editTextFirstName.text.toString().lowercase()
                val lastNameFieldUser = editTextLastName.text.toString().lowercase()
                val dniFieldUser = editTextDNI.text.toString()
                val emailFieldUser = editTextEmail.text.toString().lowercase()
                val passwordFieldUser = editTextPassword.text.toString().lowercase()
                val confirmPasswordFieldUser = editTextConfirmPassword.text.toString().lowercase()

                if (validationFields(firstNameFieldUser, lastNameFieldUser, emailFieldUser, dniFieldUser, passwordFieldUser, confirmPasswordFieldUser)) {

                    if (isPasswordAreEquals(passwordFieldUser, confirmPasswordFieldUser)) {

                        if (!isUserAlreadyExists(dniFieldUser.toLong())) {

                            // Entity instance
                            val userInstanceEntity = UserEntity(
                                dniFieldUser.toLong(),
                                firstNameFieldUser,
                                lastNameFieldUser,
                                emailFieldUser,
                                passwordFieldUser
                            )

                            // Save user
                            userViewModel.insertUser(userInstanceEntity)

                            // Save DNI in shared preferences
                            saveDniLogged(dniFieldUser.toLong())

                            // Go to fragment activity
                            goToFragmentActivity()
                        } else {
                            Toast.makeText(
                                context,
                                "DNI asociado a otra cuenta.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Verifique las contraseñas ingresadas.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Verifique que los campos no se encuentren vacios.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding.root

    }

    // Func
    private fun goToFragmentActivity() {
        val fragmentActivityIntent = Intent(requireActivity(), FragmentActivity::class.java)
        startActivity(fragmentActivityIntent)
        requireActivity().finish()
    }

    private fun validationFields(firstNameField: String, lastNameField: String, emailField: String, dniField: String, passwordField: String, confirmPasswordField: String): Boolean {
        return firstNameField.isNotEmpty() && lastNameField.isNotEmpty() && emailField.isNotEmpty() && dniField.isNotEmpty() && passwordField.isNotEmpty() && confirmPasswordField.isNotEmpty()
    }

    private fun isPasswordAreEquals(passwordField: String, confirmPasswordField: String): Boolean {
        return passwordField == confirmPasswordField
    }

    private fun isUserAlreadyExists(userDni: Long): Boolean {
        val data = userViewModel.selectCountUserFilterByDni(userDni)
        return if (data > 0) {
            true
        } else {
            false
        }
    }

    private fun saveDniLogged(dniUser: Long) {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)
        val edit = preferences.edit()

        val userSerializer = UserSerializer(dniUser)
        val userToJsonFormat = gson.toJson(userSerializer)

        edit.putString("UserDni", userToJsonFormat)
        edit.apply()
    }

}