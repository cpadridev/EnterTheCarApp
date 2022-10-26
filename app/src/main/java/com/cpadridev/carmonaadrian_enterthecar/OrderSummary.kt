package com.cpadridev.carmonaadrian_enterthecar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cpadridev.carmonaadrian_enterthecar.databinding.ActivityMainBinding
import com.cpadridev.carmonaadrian_enterthecar.databinding.OrderSummaryBinding

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
            binding.fuelLayout.isVisible = binding.vehicle.text == getString(R.string.tourism)
            if (binding.vehicle.text == getString(R.string.tourism)) {
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

        binding.btnBack.setOnClickListener {
            val person = Person(
                binding.name.text.toString(),
                binding.surnames.text.toString(),
                binding.vehicle.toString(),
                binding.fuel.toString(),
                binding.gps.text.toString() == getString(R.string.yes),
                binding.rentDays.text.toString(),
                binding.totalPrice.text.toString()
            )

            val bundle = Bundle()

            bundle?.putParcelable("Person", person)

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val person = Person(
                binding.name.text.toString(),
                binding.surnames.text.toString(),
                binding.vehicle.toString(),
                binding.fuel.toString(),
                binding.gps.text.toString() == getString(R.string.yes),
                binding.rentDays.text.toString(),
                binding.totalPrice.text.toString()
            )

            val bundle = Bundle()

            bundle?.putParcelable("Person", person)

            val intent = Intent(this, PaymentForm::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            startActivity(intent)
        }
    }
}