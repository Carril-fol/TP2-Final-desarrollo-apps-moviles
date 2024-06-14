package com.ifts4.tp2_final.ui.turn.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ifts4.tp2_final.R
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.databinding.ItemRecyclerviewTurnBinding
import com.ifts4.tp2_final.turn.viewmodel.TurnViewModel
import com.ifts4.tp2_final.ui.turn.viewholder.TurnViewHolder


class TurnAdapter(private val turnViewModel: TurnViewModel, private val context: Context): RecyclerView.Adapter<TurnViewHolder>() {

    private var turnList = emptyList<TurnEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnViewHolder {
        val binding = ItemRecyclerviewTurnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TurnViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return turnList.size
    }

    override fun onBindViewHolder(holder: TurnViewHolder, position: Int) {
        val turn = turnList.get(position)
        holder.bind(turn = turn)

        holder.itemView.findViewById<FloatingActionButton>(R.id.btnDelete).setOnClickListener {
            showDialogDeleteTurn(turn)
        }

    }

    private fun showDialogDeleteTurn(turn: TurnEntity) {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle("¿Desea eliminar el turno?")
        dialog.setMessage("¿Esta seguro que desea eliminar el turno?")

        dialog.setNegativeButton("No") { _,_ ->
            return@setNegativeButton
        }

        dialog.setPositiveButton("Yes") { _,_ ->
            turnViewModel.deleteTurn(turn)
        }

        dialog.create().show()

    }

    fun setList(turns: List<TurnEntity>) {
        turnList = turns
        notifyDataSetChanged()
    }


}