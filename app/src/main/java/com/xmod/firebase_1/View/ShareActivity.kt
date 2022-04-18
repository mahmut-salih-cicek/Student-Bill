package com.xmod.firebase_1.View

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.xmod.firebase_1.R
import kotlinx.android.synthetic.main.activity_share.*
import java.util.*

class ShareActivity : AppCompatActivity() {

    var secilenGorselUrl : Uri? = null
    var secilenBitmap : Bitmap? = null
    lateinit var storage : FirebaseStorage
    lateinit var auth : FirebaseAuth
    lateinit var database : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    fun paylas(view: android.view.View) {

        val randomUUID = UUID.randomUUID()

        /// storage işlemleri

        /// storage kayıt ederken ilk path ayarlıyoruz
        val referenca = storage.reference // deponun root kısmını en ust klosurunu ayarladık
        val gorselReferans = referenca.child("images").child("$randomUUID.jpg")

        if (secilenGorselUrl != null){

            /// storage gorseli yukleme
            gorselReferans.putFile(secilenGorselUrl!!).addOnSuccessListener {

                Toast.makeText(this, "yuklendi", Toast.LENGTH_SHORT).show()
                /// database de api yi yavastan hazırlıyoruz ilk yuklediğimiz image in linki alıcaz
                val yuklenenGorselRef = FirebaseStorage.getInstance().reference.child("images").child("$randomUUID.jpg")
                yuklenenGorselRef.downloadUrl.addOnSuccessListener {


                    //// database linki ekleyelim api için önemli :)
                    val kullanıcıResimURL = it.toString()
                    val KullanıcıEmail = auth.currentUser!!.email
                    val kullanıcıAlınanYer = alinanYer.text.toString()
                    val kullanıcıFiyat = fiyat.text.toString()
                    val kullanıcıTarih = tarih.text.toString()
                    val kullanıcıIsım = name.text.toString()
                    val tarih = Timestamp.now()

                    val postHashMap = hashMapOf<String,Any>()
                    postHashMap.put("kullanıcıResimURL",kullanıcıResimURL)
                    postHashMap.put("kullanıcıIsım",kullanıcıIsım)
                    postHashMap.put("KullanıcıEmail",KullanıcıEmail!!)
                    postHashMap.put("kullanıcıFiyat",kullanıcıFiyat)
                    postHashMap.put("kullanıcıTarih",kullanıcıTarih)
                    postHashMap.put("kullanıcıAlınanYer",kullanıcıAlınanYer)

                    postHashMap.put("tarih",tarih)

                    /// api oluşturuyoruz
                    database.collection("Post").add(postHashMap).addOnCompleteListener {
                        if (it.isSuccessful){
                            var intent = Intent(this@ShareActivity, UserActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener {
                        var alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("yukleme basarısız")
                        alertDialog.show()
                    }



                }
            }
        }

    }



    fun gorsel_sec(view: android.view.View) {

        //// izin isteme kodları
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // izin alınmadı ise izin isteği gonderilcek onRequestPermissionsResult yazicaz bunun için
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),100)

        }else{
            // eğer izin zaten varsa varsa galeri intenti ac
            val galeriIntent= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,200)

        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 100){
            if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,200)
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode==200 && resultCode == Activity.RESULT_OK){

            secilenGorselUrl = data!!.data

            if (secilenGorselUrl != null){
                if (Build.VERSION.SDK_INT >= 28){
                    var source = ImageDecoder.createSource(this.contentResolver,secilenGorselUrl!!)
                    secilenBitmap = ImageDecoder.decodeBitmap(source)
                    fisFoto.setImageBitmap(secilenBitmap)

                }else{
                    /// secilen gorseli android 27 ve dusuk tellerde set etmek
                    secilenBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,secilenGorselUrl)
                    fisFoto.setImageBitmap(secilenBitmap)

                }


            }


        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }




}

