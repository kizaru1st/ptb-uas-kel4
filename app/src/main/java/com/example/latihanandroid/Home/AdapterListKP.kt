package com.example.latihanandroid.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanandroid.R

class AdapterListKP(private val DataListKPList: ArrayList<DataListKP>) :
    RecyclerView.Adapter<AdapterListKP.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_pengajuan_kp, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = DataListKPList[position]
        holder.JudulList.text = currentItem.instansi
        holder.StatusList.text = currentItem.status
        holder.AlasanList.text = currentItem.alasan
    }

    override fun getItemCount(): Int {
        return DataListKPList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val JudulList: TextView = itemView.findViewById(R.id.tvJudul)
        val StatusList: TextView = itemView.findViewById(R.id.tvStatus)
        val AlasanList: TextView = itemView.findViewById(R.id.tvAlasan)

    }
}