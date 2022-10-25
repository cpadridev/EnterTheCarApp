package com.cpadridev.carmonaadrian_enterthecar

import android.content.Intent
import android.os.Bundle
import android.view.View.inflate
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cpadridev.carmonaadrian_enterthecar.databinding.ActivityMainBinding.inflate
import com.cpadridev.carmonaadrian_enterthecar.databinding.OrderSummaryBinding
import com.cpadridev.carmonaadrian_enterthecar.databinding.PaymentSummaryBinding.inflate

/**
@author: Adrian Carmona
 */
class OrderSummary : AppCompatActivity() {
    private lateinit var binding: OrderSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            val person: Person? = bundle?.getParcelable("Person")

            binding.name.text = person?.name
            binding.surnames.text = person?.surnames
            binding.vehicle.text = person?.vehicleType
            binding.fuelLayout.isVisible = binding.vehicle.text == getString(R.string.vehicle)
            if (binding.vehicle.text == getString(R.string.vehicle)) {
                binding.fuel.text = person?.fuelType
            }
            binding.gps.text =
                if (person?.gps == true) {
                    getString(R.string.yes)
                } else {
                    getString(R.string.no)
                }
            binding.rentDays.text = person?.days
            binding.totalPrice.text = person?.totalPrice

        }
    }
}