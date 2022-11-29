package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecarapp.databinding.PaymentFormBinding

/**
@author: Adrian Carmona
 */
class PaymentForm : AppCompatActivity() {
    private lateinit var binding: PaymentFormBinding
    private lateinit var person: Person

    // "payment" is the same as that "person".
    private lateinit var payment: Payment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PaymentFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle?.getParcelable("Person")!!

            binding.cardType.setSelection(
                when(person.payment?.cardType) {
                    getString(R.string.visa) -> 0
                    getString(R.string.mastercard) -> 1
                    else -> 2
                })
            binding.cardNumber.setText(person.payment?.cardNumber)
            binding.expirationDate.setText(person.payment?.expirationDate)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.card_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.cardType.adapter = adapter
        }

        binding.btnSend.setOnClickListener {
            val date = binding.expirationDate.text

            if (binding.cardNumber.text?.isNotEmpty()!! && binding.expirationDate.text?.isNotEmpty()!!) {
                // Detect if card number is correct
                if (binding.cardNumber.text!!.length == 19) {
                    // Detect if date is valid
                    if (date?.length == 5 && date.substring(0, 2).toInt() in 1..13
                        && date.substring(3, 5).toInt() in 1..100) {
                        payment = Payment(
                            binding.cardType.selectedItem.toString(),
                            binding.cardNumber.text.toString(),
                            binding.expirationDate.text.toString()
                        )

                        person.payment = payment

                        val bundle = Bundle()

                        bundle.putParcelable("Person", person)

                        val intent = Intent(this, PaymentSummary::class.java).apply {
                            putExtra(Intent.EXTRA_TEXT, bundle)
                        }

                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.error_valid_date),
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_valid_card), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_LONG).show()
            }
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
