package com.ifts4.tp2_final

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ifts4.tp2_final.databinding.ActivityFragmentBinding
import com.ifts4.tp2_final.ui.core.HomeFragment
import com.ifts4.tp2_final.ui.turn.ListTurnFragment
import com.ifts4.tp2_final.ui.user.ProfileUserFragment

class FragmentActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityFragmentBinding

    private lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Binding
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Navigation
        navigation = binding.navView
        navigation.setOnItemSelectedListener(this)

        // Set "HomeFragment"
        supportFragmentManager.commit {
            replace<HomeFragment>(R.id.fragmentContainer)
            addToBackStack(null)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemHome -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                    replace(R.id.fragmentContainer, HomeFragment())
                    addToBackStack(null)
                }
                true
            }
            R.id.itemCalendar -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                    replace(R.id.fragmentContainer, ListTurnFragment())
                    addToBackStack(null)
                }
                true
            }
            R.id.itemProfile -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out,
                    )
                    replace(R.id.fragmentContainer, ProfileUserFragment())
                    addToBackStack(null)
                }
                true
            }
            else -> false
        }
    }

}