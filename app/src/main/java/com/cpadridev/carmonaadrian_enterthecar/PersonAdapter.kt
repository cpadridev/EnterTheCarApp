package com.cpadridev.carmonaadrian_enterthecar

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter (private val rentals: ArrayList<Person>): RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txvNameAndSurnames: TextView
        val txvVehicle: TextView
        val txvDays: TextView
        val txvPrice: TextView

        init {
            txvNameAndSurnames = view.findViewById(R.id.txvNameAndSurnames)
            txvVehicle = view.findViewById(R.id.txvVehicle)
            txvDays = view.findViewById(R.id.txvDays)
            txvPrice = view.findViewById(R.id.txvPrice)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rentals_elements, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.txvNameAndSurnames.text = rentals[position].name + " " + rentals[position].surnames
        if (rentals[position].vehicleType == Resources.getSystem().getString(R.string.tourism)) {
            viewHolder.txvVehicle.text = rentals[position].vehicleType + " - " + rentals[position].fuelType
        } else {
            viewHolder.txvVehicle.text = rentals[position].vehicleType
        }
        viewHolder.txvDays.text = "Days: " + rentals[position].days
        viewHolder.txvPrice.text = "Price: " + rentals[position].totalPrice + "€"
    }

    override fun getItemCount() = rentals.size
}