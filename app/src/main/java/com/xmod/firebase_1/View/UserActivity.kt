package com.xmod.firebase_1.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xmod.firebase_1.*
import com.xmod.firebase_1.Model.firebasemodel
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database : FirebaseFirestore
    var postList = ArrayList<firebasemodel>()
    lateinit var localUserName :SharedPreferences

    companion object{
        var localUser = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        auth = FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()


        localUserName = getSharedPreferences("data",Context.MODE_PRIVATE)
        isimKayit.setOnClickListener {
            localUserName.edit().putString("data",UserActivityName.text.toString()).apply()
        }

        var x1 = localUserName.getString("data","").toString()
        localUser = x1
        UserActivityName.setText(localUserName.getString("data","").toString());

        //getData()





    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.exit_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.exit){
            auth.signOut()
            var intent = Intent(this, MainActivity::class.java)
            UserActivityName.setText("")
            localUserName.edit().putString("data","").apply()

            startActivity(intent)
        }else if(item.itemId == R.id.Hakk??nda){
            Toast.makeText(this, "Daha YAp??lmadi", Toast.LENGTH_SHORT).show()
        }


        return super.onOptionsItemSelected(item)
    }


    fun getData(){

        database.collection("Post")
                /// kullan??c??ya ozel datayi goster
            .whereEqualTo("Kullan??c??Email",auth.currentUser!!.email)
            .whereEqualTo("kullan??c??Is??m","$localUser")
                /// en son girelen tarih en basta gozukkecek
            .orderBy("tarih",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
            if (error != null){
                println("Hata data ??ekilmedi ")

            }else{
                if(snapshot != null){
                    if (snapshot.isEmpty == false){

                        val documents = snapshot.documents /// mutable listi ald??k

                        postList.clear()

                        for(k in documents){
                          var kullan??c??ResimURL = k.get("kullan??c??ResimURL") as String
                          var kullan??c??Is??m = k.get("kullan??c??Is??m") as String
                          var Kullan??c??Email = k.get("Kullan??c??Email") as String
                          var kullan??c??Fiyat = k.get("kullan??c??Fiyat") as String
                          var kullan??c??Tarih = k.get("kullan??c??Tarih") as String
                          var kullan??c??Al??nanYer = k.get("kullan??c??Al??nanYer") as String
                            postList.add(firebasemodel(kullan??c??ResimURL,kullan??c??Is??m,Kullan??c??Email,kullan??c??Fiyat,kullan??c??Tarih,kullan??c??Al??nanYer))
                            println(kullan??c??Is??m)
                        }
                    }
                }
            }
        }

    }

    fun FisEkle(view: android.view.View) {

       var str = localUserName.getString("data","").toString()
        if (str != ""){
            localUser = str
            val intent = Intent(this, ShareActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("L??tfen ismini gir")
            alertDialog.show()
        }


    }

    fun ShowBillingList(view: android.view.View) {

        var str = localUserName.getString("data","").toString()

        if (str != ""){
            localUser = str
            var intent = Intent(this, BillingListActivity::class.java)
            startActivity(intent)
        }else{
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("L??tfen ismini gir")
            alertDialog.show()
        }



    }


    fun Borc(view: android.view.View) {
        val intent = Intent(this, BorcActivity::class.java)
        startActivity(intent)
    }


}