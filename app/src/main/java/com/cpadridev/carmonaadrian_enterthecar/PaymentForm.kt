package com.cpadridev.carmonaadrian_enterthecar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecar.databinding.PaymentFormBinding
import java.util.*

/**
@author: Adrian Carmona
 */
class PaymentForm: AppCompatActivity() {
    private lateinit var binding: PaymentFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PaymentFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.dateExpiration.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var date = binding.dateExpiration.text

//                if (date.substring(0, 2).toInt() > 12 || date.substring(3, 5).toInt() < 0 || date.substring(3, 5).toInt() > 50)

                binding.dateExpiration.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && date.length == 3 && date.contains("/")) {
                        binding.dateExpiration.setSelection(2)
                    }
                    false
                }

                if (date.length == 2 && !date.contains("/")) {
                    binding.dateExpiration.setText("$date/")
                }
                if (date.length == 3 && date.contains("/")) {
                    binding.dateExpiration.setSelection(3)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
}
