package com.xmod.firebase_1.View

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.xmod.firebase_1.R
import kotlinx.android.synthetic.main.activity_register_activty.*

class RegisterActivty : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)

        auth = FirebaseAuth.getInstance()
    }

    fun RegisterScreenKayıt(view: android.view.View) {

       var email = registerEmail.text.toString()
        var password = registerPass.text.toString()

        println("$email")
        println("$password")


        if (email != "" && password != ""){
            auth.createUserWithEmailAndPassword(email!!,password!!).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "başarılı", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }else{
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Lütfen şifre ve email doğru gir!!")
            alertDialog.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->

            })
            alertDialog.show()

        }


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

}