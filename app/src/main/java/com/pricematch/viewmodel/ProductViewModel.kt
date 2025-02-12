package com.pricematch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pricematch.api.RetrofitCaller
import com.pricematch.model.*
import retrofit2.*

class ProductViewModel : ViewModel() {

    val categoryList = MutableLiveData<List<Category>>()  // Change from List<Product> to List<Category>
    val errorMessage = MutableLiveData<String>()

    fun fetchProducts(retryCount: Int = 3) {
        RetrofitCaller.instance.getProducts("team").enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories ?: emptyList()
                    categoryList.postValue(categories)  // Store categories instead of flattening products
                } else {
                    errorMessage.postValue("API Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                if (retryCount > 0) {
                    fetchProducts(retryCount - 1)  // Retry API call
                } else {
                    errorMessage.postValue("Network Error: ${t.message}")
                }
            }
        })
    }
}
