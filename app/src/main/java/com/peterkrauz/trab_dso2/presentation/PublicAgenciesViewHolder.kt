package com.peterkrauz.trab_dso2.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.data.entities.PublicAgency
import kotlinx.android.synthetic.main.item_public_agency.view.*

class PublicAgenciesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        item: PublicAgency,
        onAgencyClick: (PublicAgency) -> Unit
    ) = with(view) {
        item.run {
            textViewCode.text = code
            textViewDescription.text = description
            cardViewBackground.setOnClickListener {
                onAgencyClick.invoke(this)
            }
        }
    }

}