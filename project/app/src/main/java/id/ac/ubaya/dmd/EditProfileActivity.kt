package id.ac.ubaya.dmd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.user_meme_card.view.*
import org.json.JSONObject


class EditProfileActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
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

//        Image View Change
        imagePhotoProfile.setOnClickListener{
            var pick:Boolean = true
            if(pick == true){
                // Select Image dari Camera
                if(!checkCameraPermission()){
                    requestCameraPermission();

                }else PickImage();
            }
            else{
                // Select Image dari Gallery
                if(!checkGalleryPermission()){
                    requestGalleryPermission();

                }else PickImage();
            }
        }
    }

    private fun PickImage() {
        CropImage.activity().start(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        var arrayString = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermissions(
            arrayString,
            100
        )
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestGalleryPermission() {
        var arrayString = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermissions(
            arrayString,
            100
        )
    }

    private fun checkCameraPermission(): Boolean {
        // Minta ijin buka camera
        var res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        // Minta ijin buat edit foto
        var res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        // res1 dan res2 harus TRUE
        return res1 && res2;
    }

    private fun checkGalleryPermission(): Boolean {
        // Minta ijin buat edit foto
        var res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        return res2;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri

                Picasso.get().load(resultUri).into(imagePhotoProfile)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}
