package com.ifts4.tp2_final.ui.turn.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.databinding.ItemRecyclerviewTurnBinding
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TurnViewHolder(private val binding: ItemRecyclerviewTurnBinding): RecyclerView.ViewHolder(binding.root) {

    private fun convertTimeMillisToLocalDate(timeMillis: Long): LocalDate {
        /*
        Convert coming timeMillis in string to a type LocalDate
        */
        // Instant intence
        val instant = Instant.ofEpochMilli(timeMillis)

        // Zone
        val zoneId = ZoneId.systemDefault()

        return instant.atZone(zoneId).toLocalDate()
    }

    fun bind(turn: TurnEntity) {
        // Asigment with binding
        with(binding) {
            val timeMillisToLocalDate = convertTimeMillisToLocalDate(turn.dateApointment)
            // Fields
            tvCategory.text = turn.category
            tvStatus.text = turn.status
            tvDateApointment.text = timeMillisToLocalDate.toString()
        }

    }

}