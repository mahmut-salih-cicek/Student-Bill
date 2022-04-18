package com.xmod.firebase_1.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xmod.firebase_1.GorselYap
import com.xmod.firebase_1.R
import com.xmod.firebase_1.Model.firebasemodel
import kotlinx.android.synthetic.main.detail_row.view.*

class DetailRecycler(var arrayList : ArrayList<firebasemodel>) : RecyclerView.Adapter<DetailRecycler.Holder>() {


    class Holder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun Bind(firebasemodel: firebasemodel) {
            itemView.Detail_Image.GorselYap(firebasemodel.kullanıcıResimURL)
            itemView.Detail_Isim.text = firebasemodel.kullanıcıIsım
            itemView.Detail_AlinanYer.text = firebasemodel.kullanıcıAlınanYer
            itemView.Detail_Tarih.text = firebasemodel.kullanıcıTarih

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var inflater = LayoutInflater.from(parent.context).inflate(R.layout.detail_row,parent,false)
        return Holder(inflater)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.Bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}