package com.cpadridev.carmonaadrian_enterthecar

import android.app.Activity
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.core.view.isVisible
import com.cpadridev.carmonaadrian_enterthecar.databinding.ActivityMainBinding

/**
    @author: Adrian Carmona
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val DATA_RETURNED: String = "DATA RETURNED"
        const val DEFAULT_VALUE: Int = 0
    }

    private val resultOrderSummary = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val dataResult = result.data
        }
    }

    private var price: Int = 25
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.vehicles_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.vehiclesSpinner.adapter = adapter
        }

        binding.vehiclesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = binding.vehiclesSpinner.getItemAtPosition(pos).toString()
                val vehiclesArray = resources.getStringArray(R.array.vehicles_array)

                binding.noFuelSpinner.isEnabled = false
                binding.fuelSpinner.isVisible = item == vehiclesArray[0]
                binding.noFuelSpinner.isVisible = item != vehiclesArray[0]

                when(item) {
                    vehiclesArray[1] -> price = 10
                    vehiclesArray[2] -> price = 5
                }

                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text = (price * Integer.parseInt(binding.rentDays.text.toString())).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.fuel_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fuelSpinner.adapter = adapter
        }

        binding.fuelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val item = binding.fuelSpinner.getItemAtPosition(pos).toString()
                val fuelsArray = resources.getStringArray(R.array.fuel_array)

                when(item) {
                    fuelsArray[0] -> price = 25
                    fuelsArray[1] -> price = 20
                    fuelsArray[2] -> price = 15
                }

                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text = (price * Integer.parseInt(binding.rentDays.text.toString())).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.no_fuel_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.noFuelSpinner.adapter = adapter
        }

        binding.rentDays.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.rentDays.text.isNotEmpty()) {
                    binding.totalPrice.text = (price * Integer.parseInt(binding.rentDays.text.toString())).toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnRent.setOnClickListener {
            if (binding.name.text.isNotEmpty() && binding.surnames.text.isNotEmpty()  && binding.rentDays.text.isNotEmpty()) {
                val intentOrderSummary = Intent(this, OrderSummary::class.java).apply {
                    putExtra(Intent.EXTRA_TEXT, "")
                }

                resultOrderSummary.launch(intentOrderSummary)
            } else {
                Toast.makeText(this, getString(R.string.errorFillFields), Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.gmail -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://www.gmail.com")
                }
                startActivity(intent)
                true
            }
            R.id.about -> {
                Toast.makeText(this, getString(R.string.aboutContent), Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
