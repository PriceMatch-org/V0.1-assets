package com.pricematch

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.pricematch.adapter.UpdatesCarouselAdapter
import com.pricematch.databinding.ActivityMainDashboardBinding
import com.pricematch.model.UpdateItem

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainDashboardBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Sample data
        val updatesItems = listOf(
            UpdateItem(R.drawable.google_svg, "Under Maintenance", "Price Match V0 in Under Maintenance"),
            UpdateItem(R.drawable.google_svg, "Under Maintenance", "Price Match V0 in Under Maintenance"),
            UpdateItem(R.drawable.google_svg, "Under Maintenance", "Price Match V0 in Under Maintenance"),
        )

        val adapter = UpdatesCarouselAdapter(updatesItems)
        bind.Carousel.adapter = adapter

        bind.cardForFoodLayout.setOnClickListener{
            startActivity(Intent(this, FoodDashboardActivity::class.java))
//            /finish()
        }

        bind.cardCabLayout.setOnClickListener{
            val alert = AlertDialog.Builder(this)
            alert.apply {
                //setIcon(R.drawable.ic_hello)
                setTitle("Under Maintains")
                setMessage("I just wanted to greet you. I hope you are doing great!, this feature is under development")
                setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                }


            }.create().show()
        }
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val firstName = user?.displayName?.split(" ")?.get(0) ?: "User"
        bind.username.text = "Welcome $firstName to PriceMatch....."

        bind.btnlogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SingInActivity::class.java))
            finish()
        }

    }
}