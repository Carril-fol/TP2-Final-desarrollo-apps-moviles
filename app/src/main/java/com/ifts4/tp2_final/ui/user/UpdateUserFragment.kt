package com.ifts4.tp2_final.ui.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentUpdateUserBinding
import com.ifts4.tp2_final.ui.turn.AddTurnFragment
import com.ifts4.tp2_final.user.viewmodel.UserViewModel
import java.util.Locale

class UpdateUserFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentUpdateUserBinding

    // ViewModel
    private val userViewModel by viewModels<UserViewModel>()

    // Gson
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateUserBinding.inflate(inflater, container, false)

        // Key from the shared preferences
        val dataInSharedPreferences = userDataInSharedPreferences()

        // Instance user
        val user = userFromDatabase(dataInSharedPreferences)

        with(binding) {

            // Set data from the user instance
            binding.etFirstNameUpdate.setText(user.firstName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            })
            binding.etLastNameUpdate.setText(user.lastName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            })
            binding.etEmailUpdate.setText(user.email)

            // Button
            btnUpdate.setOnClickListener {
                userUpdate(user)
            }

        }

        return binding.root
    }

    private fun goToProfileFragment() {
        val fragment = ProfileUserFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun userUpdate(user: UserEntity) {
        val firstName = binding.etFirstNameUpdate.text.toString()
        val lastName = binding.etLastNameUpdate.text.toString()
        val email = binding.etEmailUpdate.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
            val newUserData = user.copy(firstName = firstName, lastName = lastName, email = email)
            userViewModel.updateUser(user=newUserData)
            goToProfileFragment()
        } else {
            Toast.makeText(
                requireContext(),
                "¡Ingrese todos los campos!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun userDataInSharedPreferences(): Long {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val userDataInSharedPreferences = preferences.getString("UserDni", null)
        val userDataFromJson = gson.fromJson(userDataInSharedPreferences, UserSerializer::class.java)
        return userDataFromJson.dni
    }

    private fun userFromDatabase(dniUser: Long): UserEntity {
        val data = userViewModel.selectUserFilterByDni(dniUser)
        return data
    }

}