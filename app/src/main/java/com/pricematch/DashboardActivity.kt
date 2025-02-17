package com.pricematch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pricematch.adapter.CategoryAdapter
import com.pricematch.adapter.FoodAdapter
import com.pricematch.databinding.ActivityDashboardBinding
import com.pricematch.viewmodel.ProductViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth
    private val productViewModel : ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardBinding.inflate(layoutInflater)
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
