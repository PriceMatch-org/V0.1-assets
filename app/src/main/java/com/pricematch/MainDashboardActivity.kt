package com.pricematch

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayoutMediator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.pricematch.adapter.UpdatesCarouselAdapter
import com.pricematch.databinding.ActivityMainDashboardBinding
import com.pricematch.model.UpdateItem

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainDashboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Sample data
        val updatesItems = listOf(
            UpdateItem(
                R.drawable.google_svg,
                "Under Maintenance",
                "Price Match V0 in Under Maintenance"
            ),
            UpdateItem(
                R.drawable.google_svg,
                "Under Maintenance",
                "Price Match V0 in Under Maintenance"
            ),
            UpdateItem(
                R.drawable.google_svg,
                "Under Maintenance",
                "Price Match V0 in Under Maintenance"
            ),
        )

        val adapter = UpdatesCarouselAdapter(updatesItems)
        bind.Carousel.adapter = adapter

        // Setup TabLayout with custom indicators
        TabLayoutMediator(bind.idTabs, bind.Carousel) { tab, _ ->
            tab.customView = layoutInflater.inflate(R.layout.custom_tab_indicator, null)
        }.attach()

        // Set initial active tab
        bind.idTabs.getTabAt(0)?.customView?.findViewById<View>(R.id.indicator)
            ?.setBackgroundResource(R.drawable.tab_indicator_active)

        // Tab selection listener
        bind.idTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<View>(R.id.indicator)
                    ?.setBackgroundResource(R.drawable.tab_indicator_active)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<View>(R.id.indicator)
                    ?.setBackgroundResource(R.drawable.tab_indicator_inactive)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Auto-scroll every 2 seconds
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            val nextItem = (bind.Carousel.currentItem + 1) % updatesItems.size
            bind.Carousel.setCurrentItem(nextItem, true)
            handler.postDelayed(runnable, 2000)
        }
        handler.postDelayed(runnable, 2000)

        // Reset auto-scroll on user interaction
        bind.Carousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

        // Other UI interactions
        bind.cardForFoodLayout.setOnClickListener {
            startActivity(Intent(this, FoodDashboardActivity::class.java))
        }

        bind.cardCabLayout.setOnClickListener {

            startActivity(Intent(this, CabDashboardActivity::class.java))


//            AlertDialog.Builder(this)
//                .setTitle("Under Maintenance")
//                .setMessage("This feature is under development.")
//                .setPositiveButton("OK", null)
//                .show()
        }

        // Firebase setup
        auth = FirebaseAuth.getInstance()
        val firstName = auth.currentUser?.displayName?.split(" ")?.get(0) ?: "User"
        bind.username.text = "Welcome $firstName to PriceMatch....."

        bind.btnlogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SingInActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}