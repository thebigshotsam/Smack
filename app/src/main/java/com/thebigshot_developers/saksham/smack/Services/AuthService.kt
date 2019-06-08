package com.thebigshot_developers.saksham.smack.Services

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.thebigshot_developers.saksham.smack.Utilities.URL_CREATE_USER
import com.thebigshot_developers.saksham.smack.Utilities.URL_LOGIN
import com.thebigshot_developers.saksham.smack.Utilities.URL_REGISTER
import com.thebigshot_developers.saksham.smack.Utilities.URL_USER_BY_EMAIL
import org.json.JSONException
import org.json.JSONObject

object AuthService {
    var isLoggedIn=false
    var  authtoken=""
    var userEmail=""
    fun registerUser(context:Context,email:String,password:String,complete:(Boolean)->Unit) {
        /*val url = URL_REGISTER
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("password", password)
        val requestbody = jsonbody.toString()
        val registerRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { _ ->
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not Register User: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(registerRequest)
    }

    }*/
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("password", password)
        val requestbody = jsonbody.toString()
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener{response->
            complete(true)

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not Register User: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(registerRequest)
    }
    fun LoginUser(context:Context,email:String,password:String,complete:(Boolean)->Unit){
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("password", password)
        val requestbody = jsonbody.toString()
        val loginrequest=object :JsonObjectRequest(Method.POST, URL_LOGIN,null,Response.Listener {response->
            //this is where we parse json oblject
            try{
                userEmail=response.getString("user")
                authtoken=response.getString("token")
                isLoggedIn=true
                complete(true)
            }catch(e:JSONException){
                Log.d("JSON","EXC:"+e.localizedMessage)
                complete(false)
            }
        },Response.ErrorListener {error->
            //this is where we deal with our error
            Log.d("ERROR", "Could not login User: $error")
            complete(false)
        }){
            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

        }
        Volley.newRequestQueue(context).add(loginrequest)
    }
    fun createUser(context:Context,name:String,email:String,avatarColor:String,avatarName:String,complete:(Boolean)->Unit){
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("name", name)
        jsonbody.put("avatarColor", avatarColor)
        jsonbody.put("avatarName", avatarName)
        val requestbody = jsonbody.toString()
        val createRequest=object :JsonObjectRequest(Method.POST, URL_CREATE_USER,null,Response.Listener {response ->
            try{
            UserDataService.name=response.getString("name")
            UserDataService.avatarName=response.getString("avatarName")
            UserDataService.id=response.getString("_id")
            UserDataService.avatarColor=response.getString( "avatarColor")
            UserDataService.email=response.getString("email")
            complete(true)
        }catch(e:JSONException){
            Log.d("JSON","EXC:"+e.localizedMessage)
            complete(false)
        }
        },Response.ErrorListener {error->
            //this is where we deal with our error
            Log.d("ERROR", "Could not Register User: $error")
            complete(false)

        }){
            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val header=HashMap<String,String>()
                header.put("Authorization","Bearer $authtoken")
                return header
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }
    fun findUserEmail(context:Context,complete:(Boolean)->Unit){

        val findUserEmailRequest=object :JsonObjectRequest(Method.GET, "$URL_USER_BY_EMAIL$userEmail",null,Response.Listener { response ->
            try{
                UserDataService.name=response.getString("name")
                UserDataService.avatarName=response.getString("avatarName")
                UserDataService.id=response.getString("_id")
                UserDataService.avatarColor=response.getString( "avatarColor")
                UserDataService.email=response.getString("email")
                complete(true)
            }catch(e:JSONException){
                Log.d("JSON","EXC:"+e.localizedMessage)
                complete(false)
            }
        },Response.ErrorListener {error->
            //this is where we deal with our error
            Log.d("ERROR", "Could not Find User: $error")
            complete(false)

        }){


            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val header=HashMap<String,String>()
                header.put("Authorization","Bearer $authtoken")
                return header
            }
        }
        Volley.newRequestQueue(context).add(findUserEmailRequest)


    }
    }
