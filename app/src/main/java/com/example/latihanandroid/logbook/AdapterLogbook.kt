package com.example.latihanandroid.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanandroid.R
import com.example.latihanandroid.logbook.DataLogbook

class AdapterLogbook(private val DataLogbook: ArrayList<DataLogbook>) :
    RecyclerView.Adapter<AdapterLogbook.MhsViewHolder>() {

    private lateinit var myListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MhsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_logbook, parent, false)
        return MhsViewHolder(itemView, myListener)
    }

    override fun onBindViewHolder(holder: MhsViewHolder, position: Int) {
        val currentItem = DataLogbook[position]
        holder.JudulDetail.text = currentItem.hari
        holder.TanggalDetail.text = currentItem.tanggal
        holder.KegiatanDetail.text = currentItem.kegiatan
    }

    override fun getItemCount(): Int {
        return DataLogbook.size
    }

    class MhsViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val JudulDetail : TextView = itemView.findViewById(R.id.tvHariKe)
        val TanggalDetail : TextView = itemView.findViewById(R.id.tvTanggal)
        val KegiatanDetail : TextView = itemView.findViewById(R.id.tvKegiatan)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}