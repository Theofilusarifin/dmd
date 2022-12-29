package id.ac.ubaya.dmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {
    var currentUser = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_setting, container, false)
        // Inflate the layout for this fragment

        // Siapin listener buat klo button fabnya diclick (Membuka activity baru)
        v.fabEditProfile.setOnClickListener {
            val parentActivity: Activity? = activity
            val intent = Intent(parentActivity, EditProfileActivity::class.java)
            requireActivity().startActivity(intent)
        }
        return v
    }

    fun getCurrentUser(){
//        Define User
        currentUser = null
        var user = Users(
            Global.user_id,
            Global.username,
            Global.firstName,
            Global.lastName,
            Global.password,
            Global.registrationDate,
            Global.urlImg,
            Global.privacySetting,
            )
//        updateUserSetting(user)
//        val q = Volley.newRequestQueue(activity)
        //Harusnya pakai 10.0.2.2 bisa asalkan di emulator android studio
        //192.168.43.237
//        val url = "http://192.168.43.237/mobile_db/get_playlist.php"
//        TEMPLATE BUAT JALANIN API
//        var stringRequest = StringRequest(
//            Request.Method.GET, url,
//            {
//                Log.d("volley_sukses", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK"){
//                    val data = obj.getJSONArray("data")
//                    for (i in 0 until data.length()){
//                        val memeObject = data.getJSONObject(i)
//                        val meme = Memes(
//                            memeObject.getInt("id"),
//                            memeObject.getString("top_text"),
//                            memeObject.getString("bottom_text"),
//                            memeObject.getString("url_img"),
//                            memeObject.getInt("num_likes"),
//                            memeObject.getInt("num_reports")
//                        )
//                        memelist.add(meme)
//                    }
////                    Log.d("cekisiarray", playlists.toString())
//                    updateMemelist()
//                }
//            },
//            {
//                Log.e("volley_gagal", it.message.toString())
//            })
//        q.add(stringRequest)
    }

//    fun updateUserSetting(users: Users) {
//        val url = users.url_img
//        Picasso.get().load(url).into(user_profile_image)
//        tv_profile_fullname.text = "${users.first_name} ${users.last_name}"
//        tv_profile_registerdate.text = "${users.registration_date}"
//        tv_profile_username.text = "${users.username}"
//        chk_box_privacy.isChecked = users.privacy
//    }

    override fun onResume() {
        super.onResume()
//        Set login user
        txtProfileName.text = Global.firstName + " " + Global.lastName
        txtProfileUsername.text = "@" + Global.username
        if (Global.privacySetting == 1){
            txtProfileTypeAccount.text = "Private Account"
        }
        else{
            txtProfileTypeAccount.text = "Public Account"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}