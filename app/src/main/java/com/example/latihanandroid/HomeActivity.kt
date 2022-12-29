package com.example.latihanandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanandroid.Home.AdapterListKP
import com.example.latihanandroid.Home.DataListKP
import com.example.latihanandroid.datamodels.GetProfileResponse
import com.example.latihanandroid.datamodels.LogoutResponse
import com.example.latihanandroid.retrofit.Api
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataListKP>
    lateinit var instansi: Array<String>
    lateinit var status: Array<String>
    lateinit var alasan: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                tvName.setText(getNama)
                tvEmail.setText(getUsername)
            }
            override fun onFailure(call: Call<GetProfileResponse?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Gaga", Toast.LENGTH_SHORT).show()
            }
        })


        btnLogout.setOnClickListener {
            val client = retrofit.create(Api::class.java)
            val call = client.logout("Bearer $token")
            call!!.enqueue(object : Callback<LogoutResponse?> {
                override fun onResponse(
                    call: Call<LogoutResponse?>,
                    response: Response<LogoutResponse?>
                ) {
                    Log.d("logout : ", response.body().toString())
                    with(sharedPref.edit()) {
                        clear()
                        apply()
                    }
                    intent = Intent(this@HomeActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                override fun onFailure(call: Call<LogoutResponse?>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Gaga", Toast.LENGTH_SHORT).show()
                }
            })
        }

        cvProfile.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnDetailKP.setOnClickListener {
            var intent = Intent(this, PengajuanKPActivity::class.java)
            startActivity(intent)
        }

        btnPengajuanKP.setOnClickListener {
            var intent = Intent(this, DetailKPActivity::class.java)
            startActivity(intent)
        }


        instansi = arrayOf(
            "Kominfo Padang",
            "Multipolar",
            "PT Semen Padang",
            "Kominfo Sumbar",
            "Disukcapil Padang",
            "Kominfo Padang",
            "Multipolar",
            "PT Semen Padang",
            "Kominfo Sumbar",
            "Disukcapil Padang",
            "Kominfo Sumbar",
            "Disukcapil Padang"
        )

        status = arrayOf(
            "Diterima",
            "Ditolak",
            "Ditolak",
            "Ditolak",
            "Ditolak",
            "Diterima",
            "Ditolak",
            "Ditolak",
            "Ditolak",
            "Ditolak",
            "Ditolak",
            "Ditolak"
        )

        alasan = arrayOf(
            "Alasan 1",
            "Alasan 2",
            "Alasan 3",
            "Alasan 4",
            "Alasan 5",
            "Alasan 6",
            "Alasan 7",
            "Alasan 8",
            "Alasan 9",
            "Alasan 10",
            "Alasan 11",
            "Alasan 12",
        )

        newRecyclerView = findViewById(R.id.rvList)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

//      Passing data to adapter
        newArrayList = arrayListOf<DataListKP>()
        getUserdata()
    }

    private fun getUserdata() {
        for(i in instansi.indices) {
            val datalist = DataListKP(instansi[i], status[i], alasan[i])
            newArrayList.add(datalist)
        }
        newRecyclerView.adapter = AdapterListKP(newArrayList)
    }
}