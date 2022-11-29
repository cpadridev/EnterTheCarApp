package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.cpadridev.carmonaadrian_enterthecarapp.databinding.ActivityMainBinding

/**
@author: Adrian Carmona
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var person: Person? = null
    private var price: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creation of the vehicle spinner.
        ArrayAdapter.createFromResource(
            this,
            R.array.vehicles_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.vehiclesSpinner.adapter = adapter
        }

        // Creation of the fuel spinner.
        ArrayAdapter.createFromResource(
            this,
            R.array.fuel_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fuelSpinner.adapter = adapter
        }

        // Creation of the not available spinner.
        ArrayAdapter.createFromResource(
            this,
            R.array.no_fuel_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.noFuelSpinner.adapter = adapter
        }

        binding.vehiclesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    // Item selected
                    val item = binding.vehiclesSpinner.getItemAtPosition(pos).toString()
                    // Get an array from string resources
                    val vehiclesArray = resources.getStringArray(R.array.vehicles_array)

                    // Disable no fuel
                    binding.noFuelSpinner.isEnabled = false
                    binding.fuelSpinner.isVisible = item == vehiclesArray[0]
                    binding.noFuelSpinner.isVisible = item != vehiclesArray[0]

                    // Change the price depending on the selected vehicle
                    when (item) {
                        vehiclesArray[1] -> price = 10
                        vehiclesArray[2] -> price = 5
                    }

                    // Calculate the price
                    if (binding.rentDays.text.isNotEmpty()) {
                        binding.totalPrice.text =
                            (price * binding.rentDays.text.toString().toInt()).toString()
                    } else {
                        binding.totalPrice.text = "0"
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        binding.fuelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val item = binding.fuelSpinner.getItemAtPosition(pos).toString()
                val fuelsArray = resources.getStringArray(R.array.fuel_array)

                when (item) {
                    fuelsArray[0] -> price = 25
                    fuelsArray[1] -> price = 20
                    fuelsArray[2] -> price = 15
                }

                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text =
                        (price * binding.rentDays.text.toString().toInt()).toString()
                } else {
                    binding.totalPrice.text = "0"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        // Detect data entry
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle?.getParcelable("Person")

            if (person != null) {
                binding.name.setText(person?.name)
                binding.surnames.setText(person?.surnames)
                binding.vehiclesSpinner.setSelection(
                    when (person?.vehicleType.toString()) {
                        getString(R.string.motorbike) -> 1
                        getString(R.string.scooter) -> 2
                        else -> 0
                    }
                )
                binding.fuelSpinner.setSelection(
                    when (person?.fuelType) {
                        getString(R.string.gasoline) -> 1
                        getString(R.string.electric) -> 2
                        else -> 0
                    }
                )
                // Recalculates price
                if (person?.vehicleType != getString(R.string.tourism)) {
                    binding.noFuelSpinner.isEnabled = false
                    binding.fuelSpinner.isVisible = false
                    binding.noFuelSpinner.isVisible = true
                    price = when (person?.vehicleType.toString()) {
                        getString(R.string.motorbike) -> 10
                        else -> 5
                    }
                }
                binding.ckxGps.isChecked = person?.gps!!
                binding.rentDays.setText(person?.days)
                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text =
                        (price * binding.rentDays.text.toString().toInt()).toString()
                }
            }
        }

        binding.rentDays.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // When it detects a change it calculates the price.
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Detect when it is not empty so that it does not break the execution
                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text =
                        (price * Integer.parseInt(binding.rentDays.text.toString())).toString()
                } else {
                    binding.totalPrice.text = "0"
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnSend.setOnClickListener {
            // It only accepts the data and changes the layout when all the fields are entered.
            if (binding.name.text.isNotEmpty() && binding.surnames.text.isNotEmpty() && binding.rentDays.text.isNotEmpty()) {
                person = Person(
                    binding.name.text.toString(),
                    binding.surnames.text.toString(),
                    binding.vehiclesSpinner.selectedItem.toString(),
                    binding.fuelSpinner.selectedItem?.toString(),
                    binding.ckxGps.isChecked,
                    binding.rentDays.text.toString(),
                    binding.totalPrice.text.toString(),
                    Payment("", "", "")
                )

                val bundle = Bundle()

                bundle.putParcelable("Person", person)

                val intent = Intent(this, OrderSummary::class.java).apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
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
            R.id.rentals -> {
                val intent = Intent(this, Rentals::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // In the main interface, it exits the application if you hit the back button.
        finishAffinity()
    }
}
