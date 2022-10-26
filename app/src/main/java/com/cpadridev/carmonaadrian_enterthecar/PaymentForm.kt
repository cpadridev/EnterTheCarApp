package com.cpadridev.carmonaadrian_enterthecar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecar.databinding.PaymentFormBinding

/**
@author: Adrian Carmona
 */
class PaymentForm: AppCompatActivity() {
    private lateinit var binding: PaymentFormBinding
    private lateinit var person: Person

    private var validDate: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PaymentFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle?.getParcelable("Person")!!
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.card_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.cardType.adapter = adapter
        }

        binding.expirationDate.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var date = binding.expirationDate.text

                binding.expirationDate.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && date.length == 3 && date.contains("/")) {
                        binding.expirationDate.setSelection(2)
                    }
                    false
                }

                if (date.length == 2 && !date.contains("/")) {
//                    if (date.substring(0, 2).toInt() < 12){
//                        validDate = false
//                    }
                    binding.expirationDate.setText("$date/")
                }
                if (date.length == 3 && date.contains("/")) {
                    binding.expirationDate.setSelection(3)
                }
//                if (date.length == 5 && date.substring(3, 5).toInt() > 0 && date.substring(3, 5).toInt() < 50) {
//                    validDate = false
//                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnSend.setOnClickListener {
            if (validDate) {
                if (binding.cardNumber.text.isNotEmpty() && binding.expirationDate.text.isNotEmpty()) {
                    val payment = Payment(
                        binding.cardType.selectedItem.toString(),
                        binding.cardNumber.text.toString(),
                        binding.expirationDate.text.toString()
                    )

                    val bundle = Bundle()

                    bundle.putParcelable("Person", person)
                    bundle.putParcelable("Payment", payment)

                    val intent = Intent(this, PaymentSummary::class.java).apply {
                        putExtra(Intent.EXTRA_TEXT, bundle)
                    }

                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.error_valid_date), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
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
