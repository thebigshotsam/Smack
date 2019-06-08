package com.thebigshot_developers.saksham.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.thebigshot_developers.saksham.smack.R
import com.thebigshot_developers.saksham.smack.Services.AuthService
import com.thebigshot_developers.saksham.smack.Services.UserDataService
import com.thebigshot_developers.saksham.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        LocalBroadcastManager.getInstance(this).registerReceiver(UserDataChangeReceiver, IntentFilter(
            BROADCAST_USER_DATA_CHANGE))

    }
    private val UserDataChangeReceiver=object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
                   if(AuthService.isLoggedIn)
                   {
                       userNameNavHeader.text=UserDataService.name
                       userEmailNavHeader.text=UserDataService.email
                       val resID=resources.getIdentifier(UserDataService.avatarName, "drawable", packageName)
                       userImageNavHeader.setImageResource(resID)
                       val CoID=UserDataService.avatarColor
                       val stripedcolor=CoID.replace("[","").replace("]","").replace(",","")

                       var r=0
                       var g=0
                       var b=0
                        val scanner=Scanner(stripedcolor)
                       if(scanner.hasNext()){
                           r=(scanner.nextDouble()*255).toInt()
                           g=(scanner.nextDouble()*255).toInt()
                           b=(scanner.nextDouble()*255).toInt()
                       }
                       userImageNavHeader.setBackgroundColor(Color.rgb(r,g,b))
                       loginBtnNavHeader.text="Logout"
                   }
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }




    fun loginBtnNavClicked(view: View) {
        if (AuthService.isLoggedIn) {
            UserDataService.logout()
            userNameNavHeader.text="No user name"
            userEmailNavHeader.text="No email"
            loginBtnNavHeader.text="LogIn"
            val resID=resources.getIdentifier("profiledefault", "drawable", packageName)
            userImageNavHeader.setImageResource(resID)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)

        } else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
    fun addChannelBtnClicked(view:View){

    }
    fun sendMessageBtnClicked(view: View){

    }

    }
