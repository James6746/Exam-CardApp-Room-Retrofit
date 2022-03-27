package com.example.modul6_exam

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.modul6_exam.database.AppDatabase
import com.example.modul6_exam.helper.Logger
import com.example.modul6_exam.model.Card
import com.example.modul6_exam.model.Responses
import com.example.modul6_exam.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initViews()
    }

    private fun initViews() {
        appDatabase = AppDatabase.getInstance(this)
        val close_btn = findViewById<ImageView>(R.id.close_btn)
        val card_number = findViewById<EditText>(R.id.card_num)
        val holder = findViewById<EditText>(R.id.holder)
        val et_cvv = findViewById<EditText>(R.id.et_cvv)
        val et_year = findViewById<EditText>(R.id.et_year)
        val et_month = findViewById<EditText>(R.id.et_month)
        val add_btn = findViewById<Button>(R.id.add_btn)

        close_btn.setOnClickListener {
            finish()
        }

        add_btn.setOnClickListener {
            val cn = card_number.text.toString()
            val hd = holder.text.toString()
            val cvv = et_cvv.text.toString()
            val expir_date = et_month.text.toString() + "/" + et_year.text.toString()
            if (cn.length == 16 && hd.isNotEmpty() && cvv.length == 3 && expir_date.length > 0) {
                val card = Card(hd, cn, expir_date, true)
                if (isInternetAvailable()) {
                    saveCardToServer(card)
                } else{
                    card.is_exsist = false
                    saveCardToDatabase(card)
                    backToFinish()
                }
            }

        }
    }

    fun backToFinish(){
        val intent = Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    private fun saveCardToServer(card: Card) {
        if (isInternetAvailable()) {
            requestServer(card)
        } else {
            card.is_exsist = false
            saveCardToDatabase(card)
        }
    }

    private fun saveCardToDatabase(card: Card){
        appDatabase.postDao().createPost(card)
    }




private fun requestServer(card: Card) {
    ApiClient.apiService.createCard(card).enqueue(object : Callback<Responses> {
        override fun onResponse(
            call: Call<Responses>,
            response: Response<Responses>
        ) {
            saveCardToDatabase(card)
            Toast.makeText(this@AddActivity, "Successfully added", Toast.LENGTH_SHORT).show()
            backToFinish()
            Logger.d("@@@", response.body().toString())

        }

        override fun onFailure(call: Call<Responses>, t: Throwable) {
            Logger.e("@@@", "Failure")
            Toast.makeText(this@AddActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

    })
}

private fun isInternetAvailable(): Boolean {
    val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    return infoMobile!!.isConnected || infoWifi!!.isConnected
}
}