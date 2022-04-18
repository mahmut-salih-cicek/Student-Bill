package com.xmod.firebase_1.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xmod.firebase_1.R
import com.xmod.firebase_1.Recycler.DetailRecycler
import com.xmod.firebase_1.Model.firebasemodel
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    lateinit var database : FirebaseFirestore
    var postList = ArrayList<firebasemodel>()
    lateinit var recycler2 : DetailRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)


        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

//        Detail_Image.GorselYap(postListPblic[0].kullanıcıResimURL)
//        Detail_AlinanYer.text = postListPblic[0].kullanıcıAlınanYer
//        Detail_Fiyat.text = postListPblic[0].kullanıcıFiyat
//        Detail_Isim.text = postListPblic[0].kullanıcıIsım
//        Detail_Tarih.text = postListPblic[0].kullanıcıTarih

        getData()

        detailRecycler.layoutManager = LinearLayoutManager(this)
        recycler2 = DetailRecycler(postList)
        detailRecycler.adapter = recycler2

    }

    fun getData(){

        database.collection("Post")
            /// kullanıcıya ozel datayi goster
            .whereEqualTo("kullanıcıIsım", BillingListActivity.uuName)
            // .whereEqualTo("kullanıcıIsım","${UserActivity.localUser}")
            /// en son girelen tarih en basta gozukkecek
            //   .orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null){
                    println("Hata data çekilmedi ")

                }else{
                    if(snapshot != null){
                        if (snapshot.isEmpty == false){

                            val documents = snapshot.documents /// mutable listi aldık

                            postList.clear()

                            for(k in documents){
                                var kullanıcıResimURL = k.get("kullanıcıResimURL") as String
                                var kullanıcıIsım = k.get("kullanıcıIsım") as String
                                var KullanıcıEmail = k.get("KullanıcıEmail") as String
                                var kullanıcıFiyat = k.get("kullanıcıFiyat") as String
                                var kullanıcıTarih = k.get("kullanıcıTarih") as String
                                var kullanıcıAlınanYer = k.get("kullanıcıAlınanYer") as String
                                postList.add(firebasemodel(kullanıcıResimURL,kullanıcıIsım,KullanıcıEmail,kullanıcıFiyat,kullanıcıTarih,kullanıcıAlınanYer))
                                println("recycler"+kullanıcıIsım)
                            }

                            recycler2.notifyDataSetChanged()

                        }
                    }
                }
            }

    }


}