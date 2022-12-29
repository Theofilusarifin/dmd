package id.ac.ubaya.dmd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    var userlist = mutableListOf<Users>()
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
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    fun getTopUser(){
//        userlist =  mutableListOf<Users>()
//        var user1 = Users(1,"Snahfu", "Hans", "Wirjawan", "12345678", "2022-12-25 00:48:00", "http://pm1.narvii.com/5734/a7a08ce9ba236846588115fdd09f3f8d733d0850_00.jpg", false)
//        var user2 = Users(2,"TheofilArifin", "Theofilus", "Arifin", "12345678", "2022-12-25 00:48:00", "http://pm1.narvii.com/5734/a7a08ce9ba236846588115fdd09f3f8d733d0850_00.jpg", false)
//        var user3 = Users(3,"GregAlvin", "Gregorius", "Alvin", "12345678", "2022-12-25 00:48:00", "http://pm1.narvii.com/5734/a7a08ce9ba236846588115fdd09f3f8d733d0850_00.jpg", false)
//        var user4 = Users(4,"Newbie", "Namanya", "Newbie", "12345678", "2022-12-25 00:48:00", "http://pm1.narvii.com/5734/a7a08ce9ba236846588115fdd09f3f8d733d0850_00.jpg", false)
//        userlist.add(user1)
//        userlist.add(user2)
//        userlist.add(user3)
//        userlist.add(user4)
//        updateLeaderboard()
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

    fun updateLeaderboard() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_leaderboard)
        recyclerView?.layoutManager = lm
        recyclerView?.adapter = LeaderboardAdapter(userlist)
        // BUAT JALANIN ANIMATIONNYA, BUTUH INI KARENA VIEWPAGER KETIKA DILOAD PERTAMA KALI BAKAL NGELOAD/JALANIN ON RESUME di semua anak2nya
        recyclerView?.startLayoutAnimation()
    }

    override fun onResume() {
        super.onResume()
        getTopUser()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}