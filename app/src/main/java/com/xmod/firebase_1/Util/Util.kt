package com.xmod.firebase_1

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.GorselYap(URl:String){

    var settings = RequestOptions
        .placeholderOf(R.color.white)
        .error(R.color.black)

    Glide.with(context).setDefaultRequestOptions(settings).load(URl).into(this)

}