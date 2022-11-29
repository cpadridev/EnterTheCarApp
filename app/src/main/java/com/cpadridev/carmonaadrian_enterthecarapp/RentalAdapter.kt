package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpadridev.carmonaadrian_enterthecarapp.model.Rental

class RentalAdapter: RecyclerView.Adapter<RentalAdapter.MyViewHolder>() {
    private var list: ArrayList<Rental> = ArrayList()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txvId: TextView
        val txvName: TextView
        val txvVehicle: TextView
        val txvDays: TextView
        val txvPrice: TextView

        init {
            txvId = view.findViewById(R.id.txvId)
            txvName = view.findViewById(R.id.txvName)
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
        viewHolder.txvId.text = list[position].id.toString()
        viewHolder.txvName.text = list[position].name
        viewHolder.txvVehicle.text = when(list[position].vehicle) {
            "tourism" -> {
                 Resources.getSystem().getString(R.string.tourism) + " - " +
                        when(list[position].price / list[position].days) {
                            25 -> Resources.getSystem().getString(R.string.diesel)
                            20 -> Resources.getSystem().getString(R.string.gasoline)
                            else -> Resources.getSystem().getString(R.string.electric)
                        }
            }
            "motorbike" -> Resources.getSystem().getString(R.string.motorbike)
            "scooter" -> Resources.getSystem().getString(R.string.scooter)
            else -> list[position].vehicle
        }
        viewHolder.txvDays.text = "Days: ${list[position].days}"
        viewHolder.txvPrice.text = "Price: ${list[position].price}€"
    }

    override fun getItemCount() = list.size

    fun addList(list_: ArrayList<Rental>){
        list.clear()
        list.addAll(list_)

        notifyDataSetChanged()
    }

    fun addList(rental: Rental) {
        list.add(rental)

        notifyDataSetChanged()
    }
}