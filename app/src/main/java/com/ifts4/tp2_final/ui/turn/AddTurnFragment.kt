package com.ifts4.tp2_final.ui.turn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.gson.Gson

import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentAddTurnBinding
import com.ifts4.tp2_final.turn.spinner.TurnCategoriesSpinner
import com.ifts4.tp2_final.turn.spinner.TurnStatusSpinner
import com.ifts4.tp2_final.turn.viewmodel.TurnViewModel
import com.ifts4.tp2_final.ui.core.HomeFragment
import java.util.Calendar

class AddTurnFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentAddTurnBinding

    // Category
    lateinit var categorySelected: TurnCategoriesSpinner

    // Calendar
    private var selectedDate: Calendar = Calendar.getInstance()

    // Turn view model
    private val turnViewModel by viewModels<TurnViewModel>()

    // Gson
    val gson = Gson()

    // Array
    val arrayCategories: Array<TurnCategoriesSpinner> = TurnCategoriesSpinner.values()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTurnBinding.inflate(inflater, container, false)

        with(binding) {
            // Buttons
            val btnRequest = BtnRequest

            // Calendar seting
            val calendarView = calendarViewTurn
            val calendarInstance = Calendar.getInstance()
            calendarView.minDate = calendarInstance.timeInMillis

            // Spinner categories
            val spinnerCategories = spinnerCategoriesTurn

            // Adapter
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayCategories)
            spinnerCategories.adapter = adapter

            // Spinner onItemSelectedListener
            spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    categorySelected = arrayCategories[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    categorySelected = arrayCategories[0]
                }
            }

            // Calendar set on date listener
            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val calendarInstance = Calendar.getInstance()
                calendarInstance.set(year, month, dayOfMonth)
                selectedDate = calendarInstance
            }

            // Data from shared preferences
            val dataInSharedPreferences = userDataInSharedPreferences()

            // Sett dni user logged
            val dniUserLogged = dataInSharedPreferences.dni

            btnRequest.setOnClickListener {
                val turn = TurnEntity(
                    0,
                    categorySelected.toString(),
                    selectedDate.timeInMillis,
                    TurnStatusSpinner.Pendiente.toString(),
                    dniUserLogged
                )
                saveTurn(turn)
            }

        }
        return binding.root
    }

    // Funcs
    private fun goToHomeFragment() {
        val fragment = HomeFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun saveTurn(turn: TurnEntity) {
        turnViewModel.insertTurn(turn)
        Toast.makeText(requireContext(), "¡Turno dado de alta!", Toast.LENGTH_SHORT).show()
        goToHomeFragment()
    }

    private fun userDataInSharedPreferences(): UserEntity   {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val userDataInSharedPreferences = preferences.getString("UserDni", null)
        val userDataFromJson = gson.fromJson(userDataInSharedPreferences, UserEntity::class.java)
        return userDataFromJson
    }

}