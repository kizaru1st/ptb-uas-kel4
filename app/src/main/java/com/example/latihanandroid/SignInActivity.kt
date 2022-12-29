package com.example.latihanandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
//Retrofit
import android.widget.EditText
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import android.widget.Toast
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.latihanandroid.datamodels.LoginResponse
import com.example.latihanandroid.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    val CHANNEL_ID_login = "channelID_login"
    val CHANNEL_NAME_login = "channelName_login"
    val NOTIFICATION_ID_login = 0
    lateinit var editEmail: EditText
    lateinit  var editPassword: EditText
    lateinit  var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val intent = Intent(this, ProfileActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_login)
            .setContentTitle("PTB A4")
            .setContentText("Berhasil Login!")
            .setSmallIcon(R.drawable.ic_star)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        cekLogin()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID_login, CHANNEL_NAME_login, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
    fun cekLogin() {
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        buttonLogin = findViewById(R.id.btnSignIn)
        buttonLogin.setOnClickListener(View.OnClickListener {
            val API_BASE_URL = "http://ptb-api.husnilkamil.my.id/api/"
            var username = editEmail.getText().toString()
            var password = editPassword.getText().toString()
            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
            val client = retrofit.create(Api::class.java)
            val call = client.login(username, password)
            call!!.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    val loginResponse = response.body()
                    Log.d("loginResponse", "login response error")
                    if (loginResponse != null) {
                        val token = response?.body()?.authorisation?.token
                        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?:return
                        with(sharedPref.edit()){
                            putString("TOKEN", token)
                            apply()
                        }
                        Log.d("Data",response.body().toString())
                        val name = response.body()?.user?.name!!.toString()
                        Toast.makeText(applicationContext, "Welcome $name", Toast.LENGTH_SHORT).show()
                        val mainIntent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@SignInActivity, "Terdapat kesalahan pada Username atau Password Anda!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    Toast.makeText(this@SignInActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        })
    }
}