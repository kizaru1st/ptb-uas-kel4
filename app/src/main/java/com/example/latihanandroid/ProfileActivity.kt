package com.example.latihanandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.latihanandroid.datamodels.GetProfileResponse
import com.example.latihanandroid.retrofit.Api
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPref = applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")

        val API_BASE_URL = "http://ptb-api.husnilkamil.my.id/api/"
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        val clientGetProfile = retrofit.create(Api::class.java)
        val callGetProfile = clientGetProfile.profile("Bearer $token")
        callGetProfile!!.enqueue(object : Callback<GetProfileResponse?> {
            override fun onResponse(
                call: Call<GetProfileResponse?>,
                response: Response<GetProfileResponse?>
            ) {
                val respon = response.body()
                val getNama = respon?.name
                val getUsername = respon?.username
                val getEmail = respon?.email
                tvNameProfile.setText(getNama)
                tvEmailProfile.setText(getEmail)
                tvUsernameProfile.setText(getUsername)
            }
            override fun onFailure(call: Call<GetProfileResponse?>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Gaga", Toast.LENGTH_SHORT).show()
            }
        })

        btnEditPassword.setOnClickListener {
            var intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
        btnReset.setOnClickListener {
            var intent = Intent(this@ProfileActivity, EditPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}