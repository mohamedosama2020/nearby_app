package com.example.nearby.features.nearbyscreen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby.R
import com.example.nearby.entities.nearby.response.Venue
import kotlinx.android.synthetic.main.venue_item_layout.view.*

class VenuesAdapter() :
    RecyclerView.Adapter<VenuesAdapter.VenueViewHolder>() {

    private lateinit var list: List<Venue>

    constructor(venues: List<Venue>) : this() {
        this.list = venues

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.venue_item_layout, parent, false)
        return VenueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venue = list[position]
        holder.displayData(venue)


    }


    class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun displayData(venue: Venue) {
            itemView.apply {
                tvTitle.text = venue.name
                tvAddress.text = venue.location.address
            }
        }

    }
}