package com.thebigshot_developers.saksham.smack.Controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.thebigshot_developers.saksham.smack.R
import com.thebigshot_developers.saksham.smack.Services.AuthService
import com.thebigshot_developers.saksham.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginspinner.visibility = View.INVISIBLE
    }
    fun createUserBtnClicked(view: View){
        val createIntent= Intent(this, CreateUserActivity::class.java)
        startActivity(createIntent)

    }
    fun loginBtnClicked(view: View) {
        hideKeyboard()
        if (!AuthService.isLoggedIn) {
            loginspinner.visibility = View.VISIBLE
            loginBtn.isEnabled = false
            createUserBtn.isEnabled=false
            val logUseremail = loginEmailTxt.text.toString()
            val passlogin = loginPasswordTxt.text.toString()
            if (logUseremail.isNotEmpty() && passlogin.isNotEmpty()) {
                AuthService.LoginUser(this, logUseremail, passlogin) { loginaccess ->
                    if (loginaccess) {
                        AuthService.findUserEmail(this) { complete ->
                            if (complete) {
                                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                loginspinner.visibility = View.INVISIBLE
                                finish()
                            } else {
                                loginspinner.visibility = View.INVISIBLE
                                Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show()
                                loginBtn.isEnabled = true
                                createUserBtn.isEnabled=true
                            }
                        }
                    }else{
                        loginspinner.visibility = View.INVISIBLE
                        Toast.makeText(this, "User Not Found, Please try again", Toast.LENGTH_LONG).show()
                        loginBtn.isEnabled = true
                        createUserBtn.isEnabled=true
                    }

                }
            }else{
                Toast.makeText(this,"Please fill both email and password",Toast.LENGTH_LONG).show()
                loginBtn.isEnabled = true
                createUserBtn.isEnabled=true
            }

        }else{
            loginspinner.visibility = View.INVISIBLE
            Toast.makeText(this, "User Already loggen in", Toast.LENGTH_LONG).show()
            loginBtn.isEnabled = true
            createUserBtn.isEnabled=true
        }
    }
    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }

    }
}
