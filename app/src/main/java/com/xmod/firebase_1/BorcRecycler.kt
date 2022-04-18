package com.xmod.firebase_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.borc_row.view.*

class BorcRecycler(var kullaniciIsım: ArrayList<firebasemodel>): RecyclerView.Adapter<BorcRecycler.Holder>() {

    class Holder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun Bind(firebasemodel: firebasemodel) {

            itemView.borc_row_isim.text = firebasemodel.kullanıcıIsım
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var inflater = LayoutInflater.from(parent.context).inflate(R.layout.borc_row,parent,false)
        return Holder(inflater)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Bind(kullaniciIsım[position])
    }

    override fun getItemCount(): Int {
        return kullaniciIsım.size
    }
}