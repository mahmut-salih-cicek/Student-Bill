package com.xmod.firebase_1.View

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.xmod.firebase_1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth

    var email: String? = null
    var password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        //// kullancı hatırlama
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }


    }


    fun kayıt_ol(view: View) {
        val intent = Intent(this, RegisterActivty::class.java)
        startActivity(intent)
        finish()
    }


    fun giris_yap(view: View) {

        var email = emailTextField.text.toString()
        var password = passwordTextField.text.toString()


        if (email != "" && password != "") {
            auth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, UserActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("email ve şifreni doldur")
        }


    }


}