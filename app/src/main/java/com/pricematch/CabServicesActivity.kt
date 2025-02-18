package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CabServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cab_services)

        val back = findViewById<TextView>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, FoodDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}