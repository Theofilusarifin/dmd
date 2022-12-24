package id.ac.ubaya.dmd

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AddMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meme)

        btn_submit.setOnClickListener{
            val q = Volley.newRequestQueue(this)
//            val url = "http://192.168.18.109/mobile_db/add_playlist.php"
            val url = "http://192.168.43.237/mobile_db/add_playlist.php"
            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener {
                    Log.d("sukses", it)
                    //Kalau berhasil balik ke playlist fragment
                    finish()
                },
                Response.ErrorListener {
                    Log.d("cekparams", it.message.toString())
                }
            )
            {
                // Set Parameter
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["title"] = til_title.editText?.text?.toString() ?: ""
                    params["subtitle"] = til_subtitle.editText?.text?.toString() ?: ""
                    params["description"] = til_description.editText?.text?.toString() ?: ""
                    params["image_url"] = til_url.editText?.text?.toString() ?: ""
                    return params
                }
            }
            q.add(stringRequest)
        }

        btn_batal.setOnClickListener{
            finish()
        }
    }
}