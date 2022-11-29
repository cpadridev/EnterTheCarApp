package com.cpadridev.carmonaadrian_enterthecarapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cpadridev.carmonaadrian_enterthecarapp.connection.ApiEnterTheCar
import com.cpadridev.carmonaadrian_enterthecarapp.connection.Client
import com.cpadridev.carmonaadrian_enterthecarapp.databinding.RentalsBinding
import com.cpadridev.carmonaadrian_enterthecarapp.model.Rental
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Rentals : AppCompatActivity() {
    private lateinit var binding: RentalsBinding

    private var retrofit: Retrofit? = null
    private var rentalAdapter: RentalAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RentalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.ryvRentals

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(this, 1))

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rentalAdapter = RentalAdapter()

        recycler.adapter = rentalAdapter

        retrofit = Client.getClient()

        getData()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        val api: ApiEnterTheCar? = retrofit?.create(ApiEnterTheCar::class.java)

        api?.getRentals()?.enqueue(object : Callback<ArrayList<Rental>> {
            override fun onResponse(call: Call<ArrayList<Rental>>, response: Response<ArrayList<Rental>>) {
                if (response.isSuccessful) {
                    val rentalsList = response.body()

                    if (rentalsList != null) {
                        rentalAdapter?.addList(rentalsList)
                    }
                } else
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_response),
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<ArrayList<Rental>>, t: Throwable) {
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