package com.ifts4.tp2_final.ui.turn

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.constants.UserCredential
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.data.serializers.UserSerializer
import com.ifts4.tp2_final.databinding.FragmentListTurnBinding
import com.ifts4.tp2_final.turn.viewmodel.TurnViewModel
import com.ifts4.tp2_final.ui.core.RegisterFragment
import com.ifts4.tp2_final.ui.turn.adapter.TurnAdapter

class ListTurnFragment: Fragment(), MenuProvider {

    private lateinit var binding: FragmentListTurnBinding

    private val turnViewModel by viewModels<TurnViewModel>()

    val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListTurnBinding.inflate(inflater, container, false)

        // Data in shared preferences
        val dataSharedPreferences = getUserDniInSharedPreferences()

        // Adapter
        val adapter = TurnAdapter(turnViewModel, requireContext())
        binding.recyclerViewTurn.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTurn.adapter = adapter

        // Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Divisor between items
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation)
        binding.recyclerViewTurn.addItemDecoration(divider)

        // Buttons
        binding.btnAdd.setOnClickListener {
            goToAddTurnFragment()
        }

        turnViewModel.readAllTurnFromTheUser(dataSharedPreferences).observe(viewLifecycleOwner) { turnList ->
            adapter.setList(turnList)
        }

        return binding.root
    }

    private fun goToAddTurnFragment() {
        val fragment = AddTurnFragment()
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("repleacement")
        }
    }

    private fun getUserDniInSharedPreferences(): Long {
        val preferences = requireActivity().getSharedPreferences(UserCredential.CREDENTIAL, Context.MODE_PRIVATE)

        val userDataInSharedPreferences = preferences.getString("UserDni", null)
        val userDataFromJson = gson.fromJson(userDataInSharedPreferences, UserSerializer::class.java)
        return userDataFromJson.dni
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}