package id.ac.ubaya.dmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedFile = "id.ac.ubaya.dmd"
        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
//        Retrieve Shared Preference
        val savedUserId = shared.getInt("id", 0)
//        If there is data login
        if (savedUserId != 0){
            Global.user_id = shared.getInt("id", 0)
            Global.username = shared.getString("username", "").toString()
            Global.firstName = shared.getString("firstName", "").toString()
            Global.lastName = shared.getString("lastName", "").toString()
            Global.password = shared.getString("password", "").toString()
            Global.registrationDate = shared.getString("registrationDate", "").toString()
            Global.urlImg = shared.getString("urlImg", "").toString()
            Global.privacySetting = shared.getInt("privacySetting", 0)

            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
//            IP Arifin
//            val url = "http://192.168.100.37/dmd/api/login.php"
            val url = "https://ubaya.fun/native/160420108/api/login.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    val obj = JSONObject(it)

                    if (obj.getString("status") == "success") {
//                        Get user Detail
                        val userDetail = JSONObject(obj.getString("user"))

//                        Save user detail into global variable
                        Global.user_id = userDetail.getInt("id")
                        Global.username = userDetail.getString("username")
                        Global.firstName = userDetail.getString("first_name")
                        Global.lastName = userDetail.getString("last_name")
                        Global.password = userDetail.getString("password")
                        Global.registrationDate = userDetail.getString("registration_date")
                        Global.urlImg = userDetail.getString("url_img")
                        Global.privacySetting = userDetail.getInt("privacy_setting")

//                        put User Detail into Shared Preference
                        val editor: SharedPreferences.Editor = shared.edit()
                        editor.putInt("id", Global.user_id)
                        editor.putString("username", Global.username)
                        editor.putString("firstName", Global.firstName)
                        editor.putString("lastName", Global.lastName)
                        editor.putString("password", Global.password)
                        editor.putString("registrationDate", Global.registrationDate)
                        editor.putString("urlImg", Global.urlImg)
                        editor.putInt("privacySetting", Global.privacySetting)
                        editor.apply()

                        Toast.makeText(this, obj.getString("msg") + " ID: " + Global.user_id, Toast.LENGTH_LONG).show()
//                        Go to Home
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error Login", Toast.LENGTH_SHORT).show()
                    Log.e("Gagal", it.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = txtComment.text.toString()
                    params["password"] = textPassword.text.toString()
                    return params
                }
            }
            queue.add(stringRequest)
        }

        buttonDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "You cannot go back", Toast.LENGTH_SHORT).show()
    }
}