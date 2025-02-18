package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pricematch.adapter.CategoryAdapter
import com.pricematch.databinding.ActivityFoodDashboardBinding
import com.pricematch.viewmodel.ProductViewModel

class FoodDashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFoodDashboardBinding

    private val productViewModel : ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFoodDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {
            productViewModel.fetchProducts()
            productViewModel.categoryList.observe(this) { categoryList  ->

                bind.recyclerView.layoutManager = LinearLayoutManager(this)
                bind.recyclerView.adapter = CategoryAdapter(categoryList)
            }
            productViewModel.errorMessage.observe(this) {er ->
                Toast.makeText(this, er, Toast.LENGTH_LONG).show()
            }
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

            bind.btnBack.setOnClickListener{
                startActivity(Intent(this, MainDashboardActivity::class.java))
                finish()
            }



        } catch (ex: Exception) {
            Log.e("FoodDashboardActivity", ex.message.toString())
        }
    }
}
