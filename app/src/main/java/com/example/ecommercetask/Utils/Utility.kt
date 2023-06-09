package com.example.ecommercetask.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Base64
import android.util.Patterns
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi

import com.example.ecommercetask.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*



class Utility {
    companion object {

//        private lateinit var prefManager: PrefManager

        val zeroRegex = "(0*[.])?0+".toRegex()
        private val nameRegex = "[a-zA-Z ]+".toRegex()
        private val mobileRegex = "^[6789]\\d{9}$".toRegex()
        private val pinRegex = "\\d{6}".toRegex()
        private val panRegex = "[A-Z]{5}[0-9]{4}[A-Z]".toRegex()
        private val floatRegex = "([0-9]*[.])?[0-9]+".toRegex()       //("\\d*\\.?\\d+")
        private val aadhaarRegex = "\\d{12}".toRegex()

        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        fun CharSequence?.isNonZeroNonEmpty() =
            !isNullOrEmpty() && !this.matches(zeroRegex)

        fun CharSequence?.isValidPhone() =
            !isNullOrEmpty() && this.matches(mobileRegex)

        fun CharSequence?.isValidPin() =
            !isNullOrEmpty() && this.matches(pinRegex)

        fun CharSequence?.isValidAadhaar() =
            !isNullOrEmpty() && this.matches(aadhaarRegex)

        fun CharSequence?.isValidName() =
            !isNullOrEmpty() && this.matches(nameRegex)

        fun showToast(context: Context, value: String) {
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
        }

        fun enableButton(button: Button) {
            button.isEnabled = true
            button.alpha = 1f
        }

        fun disableButton(button: Button) {
            button.isEnabled = false
            button.alpha = 0.55f
        }


        fun decodePicString(encodedString: String): Bitmap {
            val imageBytes = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.N)
        fun convertDateFormat(
            inputPattern: String,
            outputPattern: String,
            inputDate: String
        ): String {
            val inputDateFormat = SimpleDateFormat(inputPattern)
            val outputDateFormat = SimpleDateFormat(outputPattern)
            val dates =
                outputDateFormat.format(inputDateFormat.parse(inputDate))
            return dates.toString()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun convertTime(
            inputPattern: String,
            outputPattern: String,
            inputTime: String
        ): String {
            val inputDateFormat = SimpleDateFormat(inputPattern)
            val outputDateFormat = SimpleDateFormat(outputPattern)
            val dates =
                outputDateFormat.format(inputDateFormat.parse(inputTime))
            return dates.toString()
        }



        fun handleErrorResponse(context: Context, activity: Activity, errorBody: ResponseBody?) {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            val errorResponse: ErrorResponse? = gson.fromJson(errorBody!!.charStream(), type)
            if (errorResponse?.error.toString() == "invalid_token") {
                Toast.makeText(context, errorResponse?.error.toString(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, errorResponse?.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }






            //performing negative action


    }
}