package com.example.modul6_exam

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modul6_exam.adapter.CustomAdapter2
import com.example.modul6_exam.database.AppDatabase
import com.example.modul6_exam.helper.Logger
import com.example.modul6_exam.model.Card
import com.example.modul6_exam.model.Responses
import com.example.modul6_exam.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CustomAdapter2
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        appDatabase = AppDatabase.getInstance(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val add_btn = findViewById<ImageView>(R.id.add_btn)
        add_btn.setOnClickListener {
            openAddActivity()
        }
        saveOfflinePostsToServer()

    }

    var resultActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getCardsFromDatabase()
        }
    }

    private fun openAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        resultActivity.launch(intent)
    }

    fun saveOfflinePostsToServer() {
        val list = appDatabase.postDao().getOfflinePosts()
        if (isInternetAvailable() && list.isNotEmpty()) {
            Log.d("@@@@", list.size.toString())
            var i = 0
            addCardToServer(i, list)
        }
    }

    private fun addCardToServer(index: Int, list: List<Card>) {
        Log.d("@@@@", "addCardToServer: ")
        var card = list[index]
        card.is_exsist = true
        var ind = index
        ApiClient.apiService.createCard(card).enqueue(object : Callback<Responses> {
            override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                updateCardInDatabase(card)
                ind++
                if (ind <= list.size - 1) {
                    addCardToServer(ind, list)
                } else {
                    Log.d("@@@@", "Saved all")
                }
            }

            override fun onFailure(call: Call<Responses>, t: Throwable) {
                if (ind == list.size - 1) {
                    Log.d("@@@@", "Saved all")
                } else {

                }
                Log.e("@@@@", "Failure")
            }

        })
    }

    private fun updateCardInDatabase(card: Card) {
        appDatabase.postDao().updateCard(card.id!!,true)
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }


    override fun onResume() {
        super.onResume()
        getCardsFromDatabase()
    }

    fun getCardsFromDatabase() {
        val list1 = appDatabase.postDao().getPosts() as ArrayList<*>
        adapter = CustomAdapter2(list1 as ArrayList<Card>)
        recyclerView.adapter = adapter
    }
}