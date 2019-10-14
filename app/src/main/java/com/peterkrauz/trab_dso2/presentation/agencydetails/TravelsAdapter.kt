package com.peterkrauz.trab_dso2.presentation.agencydetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.Travel
import com.peterkrauz.trab_dso2.presentation.common.paging.PagedAdapter

class TravelsAdapter(
    private val onTravelClick: ((Travel) -> Unit)
) : RecyclerView.Adapter<TravelViewHolder>(), PagedAdapter<Travel> {

    var travels = emptyList<Travel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel, parent, false)
        return TravelViewHolder(view)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        val travel = travels[position]
        holder.bind(travel, onTravelClick)
    }

    override fun getItemCount(): Int = travels.size

    override fun submitItems(items: MutableList<Travel>) {
        val currentTravels = travels
        val newTravelsList = mutableListOf<Travel>()

        newTravelsList.addAll(currentTravels)
        newTravelsList.addAll(items)

        travels = newTravelsList
    }

    override fun clearItems() {
        travels = mutableListOf()
    }

}