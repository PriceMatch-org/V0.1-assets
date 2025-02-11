package com.pricematch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pricematch.adapter.FoodAdapter
import com.pricematch.databinding.ActivityDashboardBinding
import com.pricematch.model.Product
import com.pricematch.viewmodel.ProductViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth
    private val productViewModel : ProductViewModel by viewModels()


    fun calculateNoOfColumns(context: Context, columnWidthDp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return maxOf(2, (screenWidthDp / columnWidthDp).toInt())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {
            // Dummy Data
//            val foodList = listOf(
//                Product("Burger", "48 g", "Fast Food", 4.7, R.drawable.google_svg, "$20"),
//                Product("Pizza", "500 g", "Fast Food",4.5, R.drawable.google_svg, "$15"),
//                Product("Pasta", "350 g", "Fast Food",4.3, R.drawable.google_svg, "$12"),
//                Product("Sandwich", "250 g", "Fast Food",4.6, R.drawable.google_svg, "$8"),
//                Product("Fries", "200 g", "Fast Food",4.2, R.drawable.google_svg, "$5"),
//                Product("Salad", "300 g", "Fast Food", 4.8, R.drawable.google_svg, "$10"),
//            )

            // Set up RecyclerView with GridLayoutManager (2 columns)

            productViewModel.fetchProducts()
            productViewModel.productList.observe(this) { it ->

                bind.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
                bind.recyclerView.setHasFixedSize(true)
                bind.recyclerView.isNestedScrollingEnabled = false
//                bind.recyclerView.adapter = FoodAdapter(foodList, this)

                bind.recyclerView.adapter = FoodAdapter(it)
            }

            productViewModel.errorMessage.observe(this) {er ->
                Toast.makeText(this, er, Toast.LENGTH_LONG).show()
            }

//            var spanCount = calculateNoOfColumns(this, 180); // Dynamic column count
//            bind.recyclerView.setLayoutManager(GridLayoutManager(this, spanCount));
//            bind.recyclerView.setAdapter(FoodAdapter(foodList, this));


//            bind.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
//            bind.recyclerView.setHasFixedSize(true)
//            bind.recyclerView.isNestedScrollingEnabled = false
//            bind.recyclerView.adapter = FoodAdapter(foodList, this)


//            bind.recyclerView.layoutManager = GridLayoutManager(this, 2) // Change to 3 for 3-column layout
//            bind.recyclerView.adapter = FoodAdapter(foodList, this)

            // Search Bar Functionality
            bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    val filteredList = foodList.filter {
//                        it.productName!!.contains(newText.orEmpty(), ignoreCase = true)
//                    }
//                    (bind.recyclerView.adapter as FoodAdapter).updateList(filteredList)
                    return true
                }
            })

            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            val firstName = user?.displayName?.split(" ")?.get(0) ?: "User"
            bind.welcomeName.text = "Welcome $firstName to PriceMatch....."

            bind.logout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this, SingInActivity::class.java))
                finish()
            }

        } catch (ex: Exception) {
            Log.e("DashboardActivity", ex.message.toString())
        }
    }
}
