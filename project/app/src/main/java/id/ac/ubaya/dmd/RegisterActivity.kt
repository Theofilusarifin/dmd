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
//            Registration Checking
            var canRegister = true
            if (txtBottomText.text.toString() == "") {
                canRegister = false
                Toast.makeText(this, "Please fill your first name!", Toast.LENGTH_SHORT).show()
            }
            else if (txtLastName.text.toString() == "") {
                canRegister = false
                Toast.makeText(this, "Please fill your username!", Toast.LENGTH_SHORT).show()
            }
            else if (txtPassword.text.toString() == "") {
                canRegister = false
                Toast.makeText(this, "Please fill your password!", Toast.LENGTH_SHORT).show()
            }
            else if (txtPassword.text.toString() != txtConfirmPassword.text.toString()) {
                canRegister = false
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show()
            }

//            If all requirement are completed then do registration
            if (canRegister){
                val queue = Volley.newRequestQueue(this)
//                Nembak API
                val url = "https://ubaya.fun/native/160420108/api/register.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
//                        Result
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
                            Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()

//                            Balik ke login
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
                        params["username"] = txtLastName.text.toString()
                        params["password"] = txtPassword.text.toString()
                        params["first_name"] = txtBottomText.text.toString()
                        params["last_name"] = txtFirstName.text.toString()

                        return params
                    }
                }
                queue.add(stringRequest)
            }
        }

        btnLoginRegisterPage.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "You cannot go back", Toast.LENGTH_SHORT).show()
    }
}