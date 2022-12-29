package com.example.latihanandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanandroid.Home.AdapterListKP
import com.example.latihanandroid.Home.AdapterLogbook
import com.example.latihanandroid.Home.DataListKP
import com.example.latihanandroid.logbook.DataLogbook
import com.example.latihanandroid.logbook.DetailrvLogbook

class LogBookActivity : AppCompatActivity() {

    private lateinit var rv : RecyclerView
    private lateinit var kegList : ArrayList<DataLogbook>
    private lateinit var adapter: AdapterLogbook
    lateinit var hari: Array<String>
    lateinit var tanggal: Array<String>
    lateinit var kegiatan: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_logbook)

        rv = findViewById(R.id.rvListLogbook1)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        hari = arrayOf(
            "Hari Pertama",
            "Hari Kedua",
            "Hari Ketiga",
            "Hari Keempat",
            "Hari Kelima",
            "Hari Keenam",
            "Hari Ketujuh",
            "Hari Kedelapan"
        )

        tanggal = arrayOf(
            "1 Oktober 2022",
            "2 Oktober 2022",
            "3 Oktober 2022",
            "4 Oktober 2022",
            "5 Oktober 2022",
            "6 Oktober 2022",
            "7 Oktober 2022",
            "8 Oktober 2022"
        )

        kegiatan = arrayOf(
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
            "Kegiatan Kerja Praktik",
        )

        kegList = arrayListOf<DataLogbook>()
        getUserdata()

        adapter = AdapterLogbook(kegList)
        rv.adapter = adapter

        adapter.setOnItemClickListener(object : AdapterLogbook.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@LogBookActivity, DetailrvLogbook::class.java)
                intent.putExtra("hari", kegList[position].hari)
                intent.putExtra("tanggal", kegList[position].tanggal)
                intent.putExtra("kegiatan", kegList[position].kegiatan)
                startActivity(intent)
            }

        })
    }

    private fun getUserdata() {
        for(i in hari.indices) {
            val loglist = DataLogbook(hari[i], tanggal[i], kegiatan[i])
            kegList.add(loglist)
        }
        rv.adapter = AdapterLogbook(kegList)
    }
}