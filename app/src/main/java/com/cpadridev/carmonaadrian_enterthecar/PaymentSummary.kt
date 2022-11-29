package com.cpadridev.carmonaadrian_enterthecar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecar.databinding.PaymentSummaryBinding

/**
@author: Adrian Carmona
 */
class PaymentSummary : AppCompatActivity() {
    private lateinit var binding: PaymentSummaryBinding
    private lateinit var person: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PaymentSummaryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle!!.getParcelable("Person")!!

            binding.cardType.text = person.payment?.cardType
            binding.cardNumber.text = person.payment?.cardNumber
            binding.expirationDate.text = person.payment?.expirationDate
        }

        binding.btnAccept.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, getString(R.string.payment_made), Toast.LENGTH_LONG).show()
        }

        binding.fabSend.setOnClickListener {
            val to = arrayOf("youremail@example.com")
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "${getString(R.string.proof_payment_title)} ${person.name} ${person.surnames}"
                )
                putExtra(Intent.EXTRA_EMAIL, to)
                putExtra(
                    Intent.EXTRA_TEXT,

                    "\n ${getString(R.string.order)} ->" +
                            "\n\t${getString(R.string.vehicle)}: ${person.vehicleType}" +
                            (if (person.vehicleType == getString(R.string.tourism)) {
                                "\n\t${getString(R.string.fuel)}: ${person.fuelType.toString()}"
                            } else {
                                "".trim()
                            }) +
                            "\n\t${getString(R.string.gps)}: ${
                                if (person.gps) {
                                    getString(R.string.yes)
                                } else {
                                    getString(R.string.no)
                                }
                            }" +
                            "\n\t${getString(R.string.rent_days)}: ${person.days}" +
                            "\n\t${getString(R.string.total_price)}: ${person.totalPrice}" +
                            "\n ${getString(R.string.payment)} ->" +
                            "\n\t${getString(R.string.card_type)}: ${person.payment?.cardType}" +
                            "\n\t${getString(R.string.card_number)}: ${person.payment?.cardNumber}" +
                            "\n\t${getString(R.string.expiration_date)}: ${person.payment?.expirationDate}"
                )
            }

            val envIntent = Intent.createChooser(intent, null)
            startActivity(envIntent)
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
