package com.example.retrofit

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var progressLoader : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Function to show progress Dialog
        progressDialog()


        // On submit button

        getData.setOnClickListener {
            val postIdToFetch = editText.text
            if (postIdToFetch.isNullOrEmpty() || !postIdToFetch.isDigitsOnly() || postIdToFetch.toString()
                            .toInt() > 10 || postIdToFetch.toString().toInt() < 1
            ) {
                editText.error = "Enter digit between 1 to 10"
            } else {
                progressLoader.show()
                fetchData(editText.text.toString().toInt())
            }
        }
    }


    private fun progressDialog() {
        progressLoader = ProgressDialog(this)
        progressLoader.setTitle("Loading")
        progressLoader.setMessage("Please wait while we are fetching post...")
        progressLoader.setCancelable(false)
    }

    private fun fetchData(postID: Int) {
        val call = Api.getClient.getPost(postID)
        call.enqueue(object : retrofit2.Callback<List<PostModel>> {
            override fun onFailure(call: retrofit2.Call<List<PostModel>>, t: Throwable) {
                Log.i("MainActivity", "Error is ${t.localizedMessage}")
                Toast.makeText(this@MainActivity, "There is some error while getting post", Toast.LENGTH_SHORT).show()
                progressLoader.dismiss()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                    call: retrofit2.Call<List<PostModel>>,
                    response: Response<List<PostModel>>
            ) {
                progressLoader.dismiss()
                Log.i("MainActivity", "Data is ${response.body()}")
                for (data in response.body()!!) {
                    //println("Data is ${data.id}, ${data.title}, ${data.body}")
                    id.text = "Post Id: ${data.id.toString()}"
                    titleView.text = "Title: ${data.title}"
                    body.text = "Body: ${data.body}"
                }
            }

        })
    }



    }