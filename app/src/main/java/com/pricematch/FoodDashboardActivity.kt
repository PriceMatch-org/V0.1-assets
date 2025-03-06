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

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFoodDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {
            bind.progressBar2.visibility = android.view.View.VISIBLE
            productViewModel.fetchProducts()
            productViewModel.categoryList.observe(this) { categoryList ->
                bind.progressBar2.visibility = android.view.View.GONE
                bind.recyclerView.layoutManager = LinearLayoutManager(this)
                bind.recyclerView.adapter = CategoryAdapter(categoryList)
            }

            productViewModel.errorMessage.observe(this) { errorMessage ->
                bind.progressBar2.visibility = android.view.View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
            bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            bind.btnBack.setOnClickListener {
                startActivity(Intent(this, MainDashboardActivity::class.java))
                finish()
            }
            bind.idCartFactor.setOnClickListener {
                startActivity(Intent(this, CartActivity::class.java))
            }

        } catch (ex: Exception) {
            bind.progressBar2.visibility = android.view.View.GONE
            Log.e("FoodDashboardActivity", ex.message.toString())
        }
    }
}