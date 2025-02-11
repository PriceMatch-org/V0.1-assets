package com.pricematch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pricematch.api.RetrofitCaller
import com.pricematch.model.Product
import retrofit2.*

class ProductViewModel : ViewModel() {

    val productList = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    //create Function for Get Product
    fun fetchProducts() {
        RetrofitCaller.instance.getProducts("team").enqueue(object : Callback<List<Product>> {
            override fun onResponse(call : Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    productList.postValue(response.body())
                } else {
                    errorMessage.postValue("Api Error : ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<List<Product>?>, p1: Throwable) {
                errorMessage.postValue("Network Error : ${p1.message}")
            }
        })
    }
}