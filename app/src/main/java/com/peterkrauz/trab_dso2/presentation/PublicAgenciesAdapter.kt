package com.peterkrauz.trab_dso2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peterkrauz.trab_dso2.R
import com.peterkrauz.trab_dso2.data.entities.PublicAgency

class PublicAgenciesAdapter : RecyclerView.Adapter<PublicAgenciesViewHolder>() {

    var publicAgencies = emptyList<PublicAgency>()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicAgenciesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_public_agency, parent, false)
        return PublicAgenciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublicAgenciesViewHolder, position: Int) {
        val publicAgency = publicAgencies[position]
        holder.bind(publicAgency)
    }

    override fun getItemCount(): Int = publicAgencies.size

}