package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecarapp.connection.ApiEnterTheCar
import com.cpadridev.carmonaadrian_enterthecarapp.connection.Client
import com.cpadridev.carmonaadrian_enterthecarapp.databinding.ActivityPaymentSummaryBinding
import com.cpadridev.carmonaadrian_enterthecarapp.model.Rental
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
@author: Adrian Carmona
 */
class PaymentSummary : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentSummaryBinding
    private lateinit var person: Person

    private var retrofit: Retrofit?= null
    private var rentalAdapter: RentalAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentSummaryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            person = bundle!!.getParcelable("Person")!!

            binding.cardType.text = person.payment?.cardType
            binding.cardNumber.text = person.payment?.cardNumber
            binding.expirationDate.text = person.payment?.expirationDate
        }

        retrofit = Client.getClient()

        binding.btnAccept.setOnClickListener {
            insertData(Rental(null,
                person.name + " " + person.surnames,
                when(person.vehicleType) {
                    getString(R.string.tourism) -> "tourism"
                    getString(R.string.motorbike) -> "motorbike"
                    getString(R.string.scooter) -> "scooter"
                    else -> person.vehicleType.substring(0, person.vehicleType.indexOf(" ")) },
                person.days,
                person.totalPrice))

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

    private fun insertData(rental: Rental){
        val api: ApiEnterTheCar? = retrofit?.create(ApiEnterTheCar::class.java)

        api?.saveRent(rental.name, rental.vehicle, rental.days, rental.price)?.enqueue(object :
            Callback<Rental> {
            override fun onResponse(call: Call<Rental>, response: Response<Rental>) {
                if (response.isSuccessful) {
                    val rent = response.body()

                    if (rent != null) {
                        rentalAdapter?.addToList(rent)
                        Snackbar.make(binding.root, getString(R.string.successful_insert_rent), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.accept)){
                            }
                            .show()
                    }
                } else
                    Toast.makeText(applicationContext,getString(R.string.error_response), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Rental>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
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
