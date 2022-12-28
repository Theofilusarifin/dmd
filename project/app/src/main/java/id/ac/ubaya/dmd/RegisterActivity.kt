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
                val url = "http://192.168.100.37/dmd/api/register.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)

                        if (obj.getString("status") == "success") {
                            Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()

                            finish()
                        } else {
                            Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "Error Register", Toast.LENGTH_SHORT).show()
                        Log.e("Gagal", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = txtUsername.text.toString()
                        params["password"] = txtPassword.text.toString()
                        params["first_name"] = txtFirstName.text.toString()
                        params["last_name"] = txtLastName.text.toString()

                        return params
                    }
                }

                queue.add(stringRequest)
            } else {
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoginRegisterPage.setOnClickListener {
            finish()
        }
    }
}