package id.ac.ubaya.dmd

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_meme.*
import org.json.JSONObject

class AddMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meme)

//        Button Add Meme
        btnUpdateProfile.setOnClickListener{
            val queue = Volley.newRequestQueue(this)
//            IP Arifin
//            val url = "http://192.168.100.37/dmd/api/add_meme.php"
            val url = "https://ubaya.fun/native/160420108/api/add_meme.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    val obj = JSONObject(it)
                    if (obj.getString("status") == "success") {
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                        //Kalau berhasil balik ke home fragment
                        finish()
                    } else {
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error Add Meme", Toast.LENGTH_SHORT).show()
                    Log.e("Gagal", it.toString())
                }
            )
            {
                // Set Parameter
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["user_id"] = Global.user_id.toString()
                    params["url_img"] = txtFirstName.text.toString() ?: ""
                    params["top_text"] = txtLastName.text.toString() ?: ""
                    params["bottom_text"] = txtBottomText.text.toString() ?: ""
                    return params
                }
            }
            queue.add(stringRequest)
        }

        btnEditProfileBack.setOnClickListener{
            finish()
        }
    }
}