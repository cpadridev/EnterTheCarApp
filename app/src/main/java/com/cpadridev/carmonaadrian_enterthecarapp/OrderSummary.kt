package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cpadridev.carmonaadrian_enterthecarapp.databinding.ActivityOrderSummaryBinding


/**
@author: Adrian Carmona
 */
class OrderSummary : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSummaryBinding

    // Create a variable "person". So we can share the same person for the different functions.
    private lateinit var person: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle?.getParcelable("Person")!!

            binding.name.text = person.name
            binding.surnames.text = person.surnames
            binding.vehicle.text = person.vehicleType
            binding.fuelLayout.isVisible = binding.vehicle.text == getString(R.string.tourism)
            if (binding.vehicle.text == getString(R.string.tourism)) {
                binding.fuel.text = person.fuelType
            }
            binding.gps.text =
                if (person.gps) {
                    getString(R.string.yes)
                } else {
                    getString(R.string.no)
                }
            binding.rentDays.text = person.days.toString()
            binding.totalPrice.text = person.totalPrice.toString()
        }

        val bundle = Bundle()

        bundle.putParcelable("Person", person)

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            startActivity(intent)
        }

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, PaymentForm::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_rest, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Redirect to gmail.
            R.id.gmail -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://www.gmail.com")
                }
                startActivity(intent)
                true
            }
            // Shows the version of the application and the author.
            R.id.about -> {
                Toast.makeText(this, getString(R.string.about_content), Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
