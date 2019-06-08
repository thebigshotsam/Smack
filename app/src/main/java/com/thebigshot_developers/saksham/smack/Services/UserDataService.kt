package com.thebigshot_developers.saksham.smack.Services

object UserDataService {
    var id=""
    var name=""
    var email=""
    var avatarColor=""
    var avatarName=""
    fun logout(){
        id=""
        name=""
        avatarColor=""
        avatarName=""
        AuthService.userEmail=""
        AuthService.authtoken=""
        AuthService.isLoggedIn=false
    }

}