package com.xmod.firebase_1.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xmod.firebase_1.Model.firebasemodel
import com.xmod.firebase_1.R
import com.xmod.firebase_1.Recycler.BillingRecycler
import kotlinx.android.synthetic.main.activity_billing_list.*

class BillingListActivity : AppCompatActivity() , BillingRecycler.Listener{


    lateinit var auth: FirebaseAuth
    lateinit var database : FirebaseFirestore
    var postList = ArrayList<firebasemodel>()
    lateinit var recycler2 : BillingRecycler

    companion object{
       var  postListPblic = ArrayList<firebasemodel>()
        var uuName = ""
    }

  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing_list)

        auth = FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()

        getData()
        
        recycler.layoutManager = LinearLayoutManager(this)
        recycler2 = BillingRecycler(postList,this@BillingListActivity)
        recycler.adapter = recycler2

    }

    fun getData(){

        database.collection("Post")
            /// kullanıcıya ozel datayi goster
            .whereEqualTo("KullanıcıEmail",auth.currentUser!!.email)
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

    override fun onItemClickListener(firebasemodel: firebasemodel) {
        postListPblic.clear()
        uuName = firebasemodel.kullanıcıIsım
        postListPblic.add(firebasemodel)
        val intent = Intent(this, UserDetailActivity::class.java)
        startActivity(intent)
    }


}