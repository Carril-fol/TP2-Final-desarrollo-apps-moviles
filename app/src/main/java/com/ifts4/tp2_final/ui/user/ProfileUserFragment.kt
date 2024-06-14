package com.ifts4.tp2_final.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ifts4.tp2_final.MainActivity
import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentProfileUserBinding
import com.ifts4.tp2_final.turn.viewmodel.TurnViewModel
import com.ifts4.tp2_final.ui.turn.AddTurnFragment
import com.ifts4.tp2_final.ui.turn.adapter.TurnAdapter
import com.ifts4.tp2_final.user.viewmodel.UserViewModel
import java.util.Locale

class ProfileUserFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentProfileUserBinding

    // ViewModel userViewModel
    private val userViewModel by viewModels<UserViewModel>()

    // ViewModel turnViewModel
    private val turnViewModel by viewModels<TurnViewModel>()

    // Gson
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileUserBinding.inflate(inflater, container, false)

        // Key from shared preferences
        val dataSharedPreferences = userDataInSharedPreferences()

        // Data from the database
        val dataUserDatabase = userViewModel.selectUserFilterByDni(dataSharedPreferences.dni)

        // Length from the turns created from the user
        val turnSize = turnViewModel.countFromAllTurnsFromTheUser(dataSharedPreferences.dni).size

        // With "binding"
        with(binding) {
            // Data user fields
            tvFirstName.text = dataUserDatabase.firstName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            tvLastName.text = dataUserDatabase.lastName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            tvUserDniValue.text = dataUserDatabase.dni.toString()

            // Data turn
            tvUserProfileCountValueTurnRequested.text = turnSize.toString()

            // Buttons
            btnSetting.setOnClickListener {
                goToProfileSettingFragment()
            }

            btnLogout.setOnClickListener {
                logoutUser()
            }
        }

        // Data turns for the recycler
        val adapter = TurnAdapter(turnViewModel, requireContext())
        binding.recyclerViewTurnProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTurnProfile.adapter = adapter

        // Get all turns from the user
        turnViewModel.readAllTurnFromTheUser(dataSharedPreferences.dni).observe(viewLifecycleOwner) { turnList ->
            adapter.setList(turnList)
        }

        return binding.root
    }

    // Funcs

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(mainActivityIntent)
        requireActivity().finish()
    }

    private fun goToProfileSettingFragment() {
        val fragment = UpdateUserFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun userDataInSharedPreferences(): UserSerializer {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val userDataInSharedPreferences = preferences.getString("UserDni", null)
        val userDataFromJson = gson.fromJson(userDataInSharedPreferences, UserSerializer::class.java)
        return userDataFromJson
    }

    private fun deleteDataFromSharedPreferences() {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)
        val edit = preferences.edit()

        edit.putString("UserDni", null)
        edit.apply()
    }

    private fun logoutUser() {
        val btnLogout = binding.btnLogout
        btnLogout.setOnClickListener {
            deleteDataFromSharedPreferences()
            goToMainActivity()
        }
    }


}