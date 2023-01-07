package id.ac.ubaya.dmd

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.imagePhotoProfile
import kotlinx.android.synthetic.main.fragment_setting.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.lang.ref.WeakReference


class EditProfileActivity : AppCompatActivity() {

    // BUAT VARIABLE PENAMPUNG SEMENTARA IMAGE URI YG MAU DIUPLOAD KE SERVER
    public var imageToUpload:Bitmap? = null
    var path: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        //            Get Image
        val url = Global.urlImg
        Picasso.get().load(url).fit().centerCrop().into(imagePhotoProfile)

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
            // UPDATE USER INFORMATION KE DATABASE
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

            // UPDATE IMAGE URL PAKAI VOLLEY ATAU TAMBAHIN DI API

            // UPLOAD IMAGE KE WEBSERVICE
            // di https://ubaya.fun/native/160420108/profilpic
            uploadImage()
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
        btnAddComment.setOnClickListener{
            finish()
        }

//        Image View Change
        imagePhotoProfile.setOnClickListener{
            // Function buat manggil alert dialog
            chooseImageMethod()
        }
    }

    // Function Upload Image
    private fun uploadImage() {
        // Persiapin object buat bntu upload
        var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()

        // CEK APAKAH IMAGENYA NULL ATO NDAK? KLO NULL ARTINYA BELUM UBAH GAMBAR SAMA SEKALI
        if(imageToUpload == null){
            return
        }

        imageToUpload!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val bytes: ByteArray = outputStream.toByteArray()
        val base64ImagetoUpload:String = Base64.encodeToString(bytes, Base64.DEFAULT)
        // VOLLEY TO UPLOAD FILE
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160420108/upload_profilepic.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)
                if (obj.getString("status") == "success") {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
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
                params["uploaded_file"] = base64ImagetoUpload
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun chooseImageMethod() {
        // Declare variable for alert
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        // Set Title
        alert.setTitle("Image Source Option")
        // Set Body Message
        alert.setMessage("Please select one of these option for your image sources")

        // Set Buttons (Camera, Gallery, Cancel)
        alert.setPositiveButton("Pick using Camera"){dialogInterface, which ->
            // Cek apakah udah dibolehin sama user?
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Klo blm minta permission dengan requestCode 0
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
            } else {
                // Klo sudah lgsg panggil function
                GetPictureFromCamera()
            }
        }
        alert.setNegativeButton("Pick using Gallery"){dialogInterface, which ->
            // Cek apakah udah dibolehin sama user?
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Klo blm minta permission dengan requestCode 1
                ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            } else {
                // Klo sudah lgsg panggil function
                GetPictureFromGallery()
            }
        }
        alert.setNeutralButton("Cancel"){dialogInterface, which ->
            dialogInterface.dismiss();
        }

        // Show Alert Dialog
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    // Function buat panggil implicit Intent Camera
    private fun GetPictureFromCamera() {
        val cameraIntent = Intent()
        cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
        startActivityForResult(cameraIntent, 0)
    }

    // Function buat panggil implicit Intent Gallery
    private fun GetPictureFromGallery(){
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, 1) //one can be replaced with any action code

    }

    // Ketika baru pertama kali dijalankan akan dijalankan karena permission blm di set sama user
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            0->{
                if(grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
                    GetPictureFromCamera()
                else
                    Toast.makeText(this, "You must grant permission to access the camera.", Toast.LENGTH_LONG).show()
            }
            1->{
                if(grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
                    GetPictureFromGallery()
                else
                    Toast.makeText(this, "You must grant permission to access the gallery.", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 0){
                // requestCode 0 --> Camera
                val extras = data!!.extras
                val imageBitmap: Bitmap = extras!!.get("data") as Bitmap
                var uriFromCamera:Uri;

                // Get A Good Quality of Image
                var imageHighQuality: WeakReference<Bitmap> = WeakReference(Bitmap.createScaledBitmap(imageBitmap
                    , imageBitmap.height, imageBitmap.width, false)
                    .copy(Bitmap.Config.RGB_565, true))

                // Get the bitmap after keeping the quality intact
                var bitmapHD: Bitmap? = imageHighQuality.get()

                // Set bitmap to image view
                imagePhotoProfile.setImageBitmap(bitmapHD)

                // Update imageToUpload
                imageToUpload = bitmapHD
//                // CONVERT TO URI PROCESS
//                uriFromCamera = convertImage(bitmapHD, this)
//
//                // Set uri to image view
//                imagePhotoProfile.setImageURI(uriFromCamera)
//
//                // UPDATE imageToUpload
//                imageToUpload = uriFromCamera
            }
            else if(requestCode == 1) {
                // requestCode 1 --> Gallery
                var imageUri = data?.data //image uri ato path
                // set uri to image view
//                imagePhotoProfile.setImageURI(imageUri)

                // Convert uri to Bitmap
                var bitmapFromUri = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

                // set bitmap to image view
                imagePhotoProfile.setImageBitmap(bitmapFromUri)

                // UPDATE imageToUpload
                imageToUpload = bitmapFromUri
            }
        }
    }

//    private fun convertImage(bitmapHD: Bitmap?, context: Context): Uri {
//        var imageFolder: File = File(context.cacheDir, "images")
//        var uriResult: Uri? = null;
//        try {
//            // create folder buat nampung sementara
//            imageFolder.mkdirs()
//            // buat file untuk nampung imagenya
//            var imageFile: File = File(imageFolder, "captured_image.jpg")
//            // buat Output stream kayak di disprog
//            var stream: FileOutputStream = FileOutputStream(imageFile)
//            // compress filenya dlm bntuk jpeg format dan di execute
//            bitmapHD?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//            stream.flush()
//            stream.close()
//
//            // Dapetin urinya dan masukin project namenya
//            uriResult = FileProvider.getUriForFile(context.applicationContext, "id.ac.ubaya.dmd"+".provider",imageFile)
//        }catch (e: FileNotFoundException){
//            e.printStackTrace()
//        }
//
//        return uriResult!!;
//    }
}
