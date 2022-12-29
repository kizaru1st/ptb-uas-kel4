package com.example.latihanandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_kpactivity.*
import kotlinx.android.synthetic.main.activity_home.*

class DetailKPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kpactivity)

        cvListLogbook.setOnClickListener {
            var intent = Intent(this, LogBookActivity::class.java)
            startActivity(intent)
        }

        cvInputDataSeminar.setOnClickListener {
            var intent = Intent(this, InputDataSeminarActivity::class.java)
            startActivity(intent)
        }

        cvLaporanAkhirKP.setOnClickListener {
            var intent = Intent(this, LaporanAkhirKPActivity::class.java)
            startActivity(intent)
        }

        cvBalasanPermohonanKP.setOnClickListener {
            var intent = Intent(this, BalasanPermohonanKPActivity::class.java)
            startActivity(intent)
        }
    }
}