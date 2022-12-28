package id.ac.ubaya.dmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnDaftar.setOnClickListener {
            if (txtPassword.text.toString() == txtConfirmPassword.text.toString()) {
                val queue = Volley.newRequestQueue(this)
                val url = "http://192.168.1.66/myLulus/register.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.d("Berhasil", it)
                        val obj = JSONObject(it)

                        if (obj.getString("result") == "success") {
                            Toast.makeText(this, "Register Success", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show()
                        }
                    },
                    Response.ErrorListener {
                        Log.e("Gagal", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["nrp"] = txtNRP.text.toString()
                        return params
                    }
                }

                queue.add(stringRequest)
            } else {
                Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoginRegisterPage.setOnClickListener {
            finish()
        }
    }
}