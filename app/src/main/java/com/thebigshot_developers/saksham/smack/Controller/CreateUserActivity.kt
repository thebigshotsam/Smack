package com.thebigshot_developers.saksham.smack.Controller

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thebigshot_developers.saksham.smack.R
import com.thebigshot_developers.saksham.smack.Services.AuthService
import com.thebigshot_developers.saksham.smack.Services.UserDataService
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlin.random.Random


class CreateUserActivity : AppCompatActivity() {
    var count =0
    var userAvatar=""
    var usercolor="[0.5, 0.5, 0.5, 1]"
     val iconlist= listOf("dark0","dark1","dark2","dark3","dark4","dark5","dark6","dark7","dark8","dark9","dark10"
         ,"dark11","dark12","dark13","dark14","dark15","dark16","dark17","dark18","dark19","dark20","dark21","dark22","dark23","dark24"
         ,"dark25","dark26","dark27","light0","light1","light2","light3","light4","light5","light6","light7","light8","light9","light10"
         ,"light11","light12","light13","light14","light15","light16","light17","light18","light19","light20","light21","light22","light23"
         ,"light24","light25","light26","light27")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }
    @SuppressLint("ResourceAsColor")
    fun generateavatarClicked(view: View){
        if(count<=iconlist.lastIndex){
            val resId=resources.getIdentifier(iconlist[count], "drawable", packageName)
            userAvatar=iconlist[count]
        createUserAvatarImageview.setImageResource(resId)

        count++
        }else{
            count=0
        }

    }
    fun generateBackgroundBtnClicked(view:View){

      val random: Random = Random
        val r=random.nextInt(255)
        val g=random.nextInt(255)
        val b=random.nextInt(255)
        createUserAvatarImageview.setBackgroundColor(Color.rgb(r,g,b))
        val savr=r.toDouble()/255
        val savg=g.toDouble()/255
        val savb=b.toDouble()/255
        usercolor="[$savr,$savg,$savb,1]"
    }
    fun createUserBtnClicked(view: View){
        val Email=createEmailTxt.text.toString()
        val password=createPasswordTxt.text.toString()
        val userName=createUserNameTxt.text.toString()
      AuthService.registerUser(this,Email,password){registerSuccess->
          if(registerSuccess){
              AuthService.LoginUser(this,Email,password){LoginSucces->
                  if(LoginSucces){
                      AuthService.createUser(this,userName,Email,usercolor,userAvatar){completeSuccess->
                          if(completeSuccess)
                          {
                              println(UserDataService.email)
                              println(UserDataService.name)
                              println(UserDataService.avatarName)
                              println(UserDataService.avatarColor)
                          }

                      }
                  }
              }
          }

      }

    }
}
