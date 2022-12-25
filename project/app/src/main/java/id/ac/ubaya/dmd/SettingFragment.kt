package id.ac.ubaya.dmd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.layout_item_peringkat_emas.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var currentUser = ""
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
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    fun getCurrentUser(){
        currentUser = ""
        var user = Users(1,"Snahfu", "Hans", "Wirjawan", "12345678", "2022-12-25 00:48:00", "http://pm1.narvii.com/5734/a7a08ce9ba236846588115fdd09f3f8d733d0850_00.jpg", false)
        updateUserSetting(user)
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

    fun updateUserSetting(users: Users) {
        val url = users.url_img
        Picasso.get().load(url).into(user_profile_image)
        tv_profile_fullname.text = "${users.first_name} ${users.last_name}"
        tv_profile_registerdate.text = "${users.registration_date}"
        tv_profile_username.text = "${users.username}"
        chk_box_privacy.isChecked = users.privacy
    }

    override fun onResume() {
        super.onResume()
        getCurrentUser()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}