package com.example.latihanandroid.logbook

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.latihanandroid.R

class DetailrvLogbook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_logbook_detail)

        val judulKegiatan : TextView = findViewById(R.id.tvJudulDetail)
        val tanggalKegiatan : TextView = findViewById(R.id.tvTanggalDetail)
        val detailKegiatan : TextView = findViewById(R.id.tvKegiatanDetail)

        val bundle : Bundle? = intent.extras
        val judul = bundle!!.getString("hari")
        val tanggal = bundle.getString("tanggal")
        val kegiatan = bundle.getString("kegiatan")

        judulKegiatan.text = judul
        tanggalKegiatan.text = tanggal
        detailKegiatan.text = kegiatan
    }
}