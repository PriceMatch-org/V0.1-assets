package com.pricematch.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.pricematch.api.RetrofitCaller
import com.pricematch.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = RetrofitCaller.createApiService(application.applicationContext)

    val categoryList = MutableLiveData<List<Category>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchProducts(page: Int = 1, limit: Int = 10) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getProducts("team", page, limit)
                categoryList.postValue(response.categories)
            } catch (e: Exception) {
                errorMessage.postValue("Network Error: ${e.message}")
            }
        }
    }
}
