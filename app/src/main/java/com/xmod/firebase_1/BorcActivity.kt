package com.xmod.firebase_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class BorcActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    lateinit var database : FirebaseFirestore
    var postList = ArrayList<firebasemodel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borc)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

       // getDataName()

    }


    fun getDataName(name:String){

        database.collection("Post")
            /// kullanıcıya ozel datayi goster
            .whereEqualTo("KullanıcıEmail",auth.currentUser!!.email)
            .whereEqualTo("kullanıcıIsım",name)
           // .whereEqualTo("kullanıcıIsım","${UserActivity.localUser}")
            /// en son girelen tarih en basta gozukkecek
            .orderBy("tarih", Query.Direction.DESCENDING)
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
                                println(kullanıcıIsım)
                            }
                        }
                    }
                }
            }

    }


    fun getDataPrice(){

        database.collection("Post")
            /// kullanıcıya ozel datayi goster
            .whereEqualTo("KullanıcıEmail",auth.currentUser!!.email)
            // .whereEqualTo("kullanıcıIsım","${UserActivity.localUser}")
            /// en son girelen tarih en basta gozukkecek
            .orderBy("tarih", Query.Direction.DESCENDING)
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
                                println(kullanıcıIsım)
                            }
                        }
                    }
                }
            }

    }


}