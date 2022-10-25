package com.cpadridev.carmonaadrian_enterthecar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecar.databinding.PaymentFormBinding
import java.util.*

/**
@author: Adrian Carmona
 */
class PaymentForm: AppCompatActivity() {
    private lateinit var binding: PaymentFormBinding
    private lateinit var inputDateOfBirth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PaymentFormBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}