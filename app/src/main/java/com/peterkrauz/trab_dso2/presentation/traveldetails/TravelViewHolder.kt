package com.peterkrauz.trab_dso2.presentation.traveldetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.data.entities.Travel

class TravelViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        item: Travel,
        onTravelClick: (Travel) -> Unit
    ) = with(view){
        item.run {

        }
    }

}