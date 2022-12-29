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
import com.example.latihanandroid.datamodels.UpdatePasswordResponse
import com.example.latihanandroid.retrofit.Api
import kotlinx.android.synthetic.main.activity_edit_password.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback

class EditPasswordActivity : AppCompatActivity() {
    val CHANNEL_ID_EditPassword = "channelID_EditPassword"
    val CHANNEL_NAME_EditPassword = "channelName_EditPassword"
    val NOTIFICATION_ID_EditPassword = 0
    lateinit var editPasswordLama: EditText
    lateinit  var editPasswordBaru: EditText
    lateinit  var editPasswordVerif: EditText
    lateinit  var buttonSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val intent = Intent(this, ProfileActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_EditPassword)
            .setContentTitle("PTB A4")
            .setContentText("Berhasil Update Password!")
            .setSmallIcon(R.drawable.ic_star)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        btnEditPassword.setOnClickListener {
            savePassword()
            notificationManager.notify(NOTIFICATION_ID_EditPassword, notification)
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID_EditPassword, CHANNEL_NAME_EditPassword, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun savePassword() {
        val sharedPref = applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")

        editPasswordLama = findViewById(R.id.editOldPasswordProfile)
        editPasswordBaru = findViewById(R.id.editNewPasswordProfile)
        editPasswordVerif = findViewById(R.id.editVerifPasswordProfile)
        buttonSave = findViewById(R.id.btnEditPassword)
        var passLamaProfile = editPasswordLama.getText().toString()
        var passBaruProfile = editPasswordBaru.getText().toString()
        var passVerifProfile = editPasswordVerif.getText().toString()

        val API_BASE_URL = "http://ptb-api.husnilkamil.my.id/api/"
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        val client = retrofit.create(Api::class.java)
        val call: Call<UpdatePasswordResponse> = client.updatePassword(token = "Bearer $token", passLamaProfile, passBaruProfile, passVerifProfile)
        call.enqueue(object: Callback<UpdatePasswordResponse> {
            override fun onFailure(call: Call<UpdatePasswordResponse>, t: Throwable) {
                Log.d("update-debug",t.localizedMessage)
            }
            override fun onResponse(call: Call<UpdatePasswordResponse>, response: Response<UpdatePasswordResponse>) {
                val respon: UpdatePasswordResponse? = response.body()
                if (respon != null && respon.status == "success" && passBaruProfile == passVerifProfile) {
                    Toast.makeText(this@EditPasswordActivity, "Berhasil Mengupdate password", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@EditPasswordActivity, "Terdapat Kesalahan dalam mengupdate Password", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}
