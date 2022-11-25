package com.cpadridev.carmonaadrian_enterthecar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpadridev.carmonaadrian_enterthecar.databinding.RentalsBinding

class Rentals : AppCompatActivity() {
    private lateinit var binding: RentalsBinding

    private var rentals: ArrayList<Person> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RentalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            rentals = bundle?.getParcelableArrayList("Rentals")!!
        }

        val recycler = binding.ryvRentals

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(this, 1))

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = PersonAdapter(rentals)

        binding.btnBack.setOnClickListener {
            if (rentals.size != 0) {
                val bundle = Bundle()

                bundle.putParcelableArrayList("Rentals", rentals)

                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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