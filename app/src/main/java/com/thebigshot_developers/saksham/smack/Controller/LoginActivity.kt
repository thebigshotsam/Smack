package com.thebigshot_developers.saksham.smack.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thebigshot_developers.saksham.smack.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun createUserBtnClicked(view: View){
        val createIntent= Intent(this, CreateUserActivity::class.java)
        startActivity(createIntent)

    }
    fun loginBtnClicked(view: View){

    }
}
