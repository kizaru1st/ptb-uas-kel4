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
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.latihanandroid.datamodels.LoginResponse
import com.example.latihanandroid.datamodels.UpdateProfileResponse
import com.example.latihanandroid.retrofit.Api
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfileActivity : AppCompatActivity() {
    val CHANNEL_ID_EditProfile = "channelID_EditProfile"
    val CHANNEL_NAME_EditProfile = "channelName_EditProfile"
    val NOTIFICATION_ID_EditProfile = 0
    lateinit var editEmail: EditText
    lateinit  var editNama: EditText
    lateinit  var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val intent = Intent(this, ProfileActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_EditProfile)
            .setContentTitle("PTB A4")
            .setContentText("Berhasil Edit Profile!")
            .setSmallIcon(R.drawable.ic_star)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)

        btnReset.setOnClickListener {
            saveProfile()
            notificationManager.notify(NOTIFICATION_ID_EditProfile, notification)
            var intent = Intent(this@EditProfileActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID_EditProfile, CHANNEL_NAME_EditProfile, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun saveProfile() {
        val sharedPref = applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")

        editEmail = findViewById(R.id.editEmailProfile)
        editNama = findViewById(R.id.editNamaProfile)
        buttonSave = findViewById(R.id.btnReset)
        var nameProfile = editNama.getText().toString()
        var emailProfile = editEmail.getText().toString()

        val API_BASE_URL = "http://ptb-api.husnilkamil.my.id/api/"
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        val client = retrofit.create(Api::class.java)
        val call: Call<UpdateProfileResponse> = client.updateProfile(token = "Bearer $token", nameProfile, emailProfile)
        call!!.enqueue(object : Callback<UpdateProfileResponse?> {
            override fun onResponse(
                call: Call<UpdateProfileResponse?>,
                response: Response<UpdateProfileResponse?>
            ) {
                val respon: UpdateProfileResponse? = response.body()
                if (respon != null && respon.status == "success" ) {
                    Toast.makeText(this@EditProfileActivity, "Berhasil Mengupdate Profile", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@EditProfileActivity, "Terdapat Kesalahan dalam mengupdate Profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse?>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}