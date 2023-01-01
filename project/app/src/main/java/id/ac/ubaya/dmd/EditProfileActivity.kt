package id.ac.ubaya.dmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.json.JSONObject

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //        Set login user data
        txtFirstName.setText(Global.firstName)
        txtLastName.setText(Global.lastName)
        var tempPrivacy = Global.privacySetting

        if (Global.privacySetting == 1){
            imgCheckBoxPrivacySetting.setImageResource(R.drawable.ic_baseline_check_box_24)
        }
        else{
            imgCheckBoxPrivacySetting.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24)
        }

//        Button Update
        btnUpdateProfile.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
//            IP Arifin
//            val url = "http://192.168.100.37/dmd/api/update_profile.php"
            val url = "https://ubaya.fun/native/160420108/api/update_profile.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    val obj = JSONObject(it)
                    if (obj.getString("status") == "success") {
//                        Update Global Variable
                        Global.firstName = txtFirstName.text.toString()
                        Global.lastName = txtLastName.text.toString()
                        Global.privacySetting = tempPrivacy

                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                        //Kalau berhasil balik ke profile fragment
                        finish()
                    } else {
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    }

                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error Update Profile", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Gagal", it.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["user_id"] = Global.user_id.toString()
                    params["first_name"] = txtFirstName.text.toString()
                    params["last_name"] = txtLastName.text.toString()
                    params["privacy_setting"] = tempPrivacy.toString()
                    return params
                }
            }
            queue.add(stringRequest)

        }
//        Checked Privacy Setting
        imgCheckBoxPrivacySetting.setOnClickListener {
            if (tempPrivacy == 1){
                imgCheckBoxPrivacySetting.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24)
                tempPrivacy = 0
            }
            else{
                imgCheckBoxPrivacySetting.setImageResource(R.drawable.ic_baseline_check_box_24)
                tempPrivacy = 1
            }
        }

//        Button back
        btnEditProfileBack.setOnClickListener{
            finish()
        }
    }
}