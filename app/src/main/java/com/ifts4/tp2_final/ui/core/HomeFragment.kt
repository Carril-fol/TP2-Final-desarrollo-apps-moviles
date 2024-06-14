package com.ifts4.tp2_final.ui.core

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.gson.Gson

import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentHomeBinding
import com.ifts4.tp2_final.ui.turn.AddTurnFragment
import com.ifts4.tp2_final.ui.turn.ListTurnFragment
import com.ifts4.tp2_final.user.viewmodel.UserViewModel
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val userViewModel by viewModels<UserViewModel>()

    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val dataInSharedPreference = userDataInSharedPreferences()

        val dataDatabase = userViewModel.selectUserFilterByDni(dataInSharedPreference)

        with(binding) {

            homeFirstName.text = dataDatabase.firstName.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else it.toString()
            }

            val cardAddTurn = binding.materialCardVIewNewDate
            val cardDates = binding.materialCardVIewDates

            cardAddTurn.setOnClickListener {
                goToAddTurnFragment()
            }

            cardDates.setOnClickListener {
                goToListTurnFragment()
            }
        }
        return binding.root
    }

    private fun goToAddTurnFragment() {
        val fragment = AddTurnFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun goToListTurnFragment() {
        val fragment = ListTurnFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun userDataInSharedPreferences(): Long {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val userDataInSharedPreferences = preferences.getString("UserDni", null)
        val userDataFromJson = gson.fromJson(userDataInSharedPreferences, UserSerializer::class.java)
        return userDataFromJson.dni
    }

}