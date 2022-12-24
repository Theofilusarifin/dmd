package id.ac.ubaya.dmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
//  Declare ArrayList of Memes
    var memelist:ArrayList<Memes> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment

        // Siapin listener buat klo button fabnya diclick (Membuka activity baru)
        v.fab_addmeme.setOnClickListener {
            val parentActivity: Activity? = activity
            val intent = Intent(parentActivity, AddMemeActivity::class.java)
            requireActivity().startActivity(intent)
        }
        return v
    }

    fun getPlaylist(){
        memelist = ArrayList()
        var meme1 = Memes(1,"Top Text", "Bottom Text", "https://www.generatormix.com/images/meme/stahp.jpg", 1,1)
        var meme2 = Memes(2,"Coba Top Text", "Coba Bottom Text", "https://www.generatormix.com/images/meme/stahp.jpg", 1,1)
        var meme3 = Memes(3,"Panjang Banget Top Text", "Panjang Banget Bottom Text", "https://www.generatormix.com/images/meme/stahp.jpg", 1,1)
        memelist.add(meme1)
        memelist.add(meme2)
        memelist.add(meme3)
        updateMemelist()
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

    fun updateMemelist() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_listofmemes)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = MemelistAdapter(memelist)
    }

    override fun onResume() {
        super.onResume()
        getPlaylist()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}