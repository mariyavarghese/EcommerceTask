package com.example.ecommercetask

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecommercetask.Utils.Constant
import com.example.ecommercetask.Utils.Utility
import com.example.ecommercetask.model.ErrorResponse
import com.example.ecommercetask.model.ProductReqModel
import com.example.ecommercetask.model.ProductResponseModel
import com.example.ecommercetask.network.RetrofitService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oges.icenter.R
import com.oges.icenter.databinding.ProductDetailsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ProductDetails : AppCompatActivity() {
    lateinit var binding: ProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.product_details)
        lifecycleScope.launch {
//                getCustomerList()
            getProductDetails()

        }
    }

    private fun getProductDetails() {
        val baseUserReqModel = ProductReqModel(
            lang = "en",
            store = "KWD"
        )

        RetrofitService.getInstance(Constant.BASE_URL).display(
            baseUserReqModel
        ).enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("response","="+response)
                    val resp = response.body()
                    resp?.let {
                        if (resp.status == Constant.SUCCESS_CODE  ) {
                            val dataobj = resp.data

                            if (dataobj != null) {
                                binding.productName.setText(dataobj.brandName)
                                binding.available.setText(dataobj.name)
                                binding.sku1.setText(dataobj.sku)
                                binding.thePriceOfProduct.setText((dataobj.price))
                                Glide.with(getApplicationContext())
                                    .load(dataobj.image)
                                    .into(binding.photo);
                                binding.description.setText(dataobj.description)
                            }



                        }
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    val error = errorResponse?.error.toString()

                        lifecycleScope.launch {
                            delay(2000)
                            getProductDetails()
                        }

                }
            }
            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {
                print(t.printStackTrace())
            }

        })
    }
}

private fun encodeImage(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG, 65, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
//            return Base64.encodeToString(b, Base64.NO_WRAP)
}


