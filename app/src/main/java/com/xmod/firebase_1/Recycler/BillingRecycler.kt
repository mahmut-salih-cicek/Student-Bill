package com.xmod.firebase_1.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xmod.firebase_1.Model.firebasemodel
import com.xmod.firebase_1.R
import kotlinx.android.synthetic.main.item_row.view.*

class BillingRecycler(var arrayList :ArrayList<firebasemodel>, var listener: Listener):RecyclerView.Adapter<BillingRecycler.Holder>() {


    interface Listener{
        fun onItemClickListener(firebasemodel: firebasemodel)
    }


    class Holder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun Bind(firebasemodel: firebasemodel, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClickListener(firebasemodel)
            }
            itemView.row_Text.text = firebasemodel.kullanıcıIsım
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return Holder(layoutInflater)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
      holder.Bind(arrayList[position],listener)
    }

    override fun getItemCount(): Int {
      return arrayList.size
    }

}