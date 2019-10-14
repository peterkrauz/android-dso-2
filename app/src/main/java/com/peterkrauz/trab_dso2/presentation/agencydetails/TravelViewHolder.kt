package com.peterkrauz.trab_dso2.presentation.agencydetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.data.entities.Travel
import kotlinx.android.synthetic.main.item_travel.view.*

class TravelViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        item: Travel,
        onTravelClick: ((Travel) -> Unit)
    ) = with(view) {
        item.run {
            textViewPersonName.text = personName
            textViewTakeoff.text = startDate
            textViewLanding.text = endDate

            cardViewBackground.setOnClickListener {
                onTravelClick.invoke(this)
            }
        }
    }

}