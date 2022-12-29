package id.ac.ubaya.dmd

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LeaderboardFragment : Fragment() {
    var leaderboardList:ArrayList<Leaderboards> = ArrayList()
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

    fun getLeaderboard(){

        val queue = Volley.newRequestQueue(activity)
//        IP Arifin
        val url = "http://192.168.100.37/dmd/api/get_leaderboard.php"
        leaderboardList =  ArrayList()

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)

                if (obj.getString("status") == "success") {
                    val leaderboards = obj.getJSONArray("leaderboards")
                    for (i in 0 until leaderboards.length()){
                        val leaderboardDetail = leaderboards.getJSONObject(i)
                        val leaderboard = Leaderboards(
                            leaderboardDetail.getInt("id"),
                            leaderboardDetail.getString("first_name"),
                            leaderboardDetail.getString("last_name"),
                            leaderboardDetail.getString("url_img"),
                            leaderboardDetail.getInt("privacy_setting"),
                            leaderboardDetail.getInt("total_like"),
                        )
                        leaderboardList.add(leaderboard)
                    }
//                    Update Leaderboard
                    updateLeaderboard()
                } else {
                    Toast.makeText(activity, obj.getString("msg"), Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Error Get Leaderboard", Toast.LENGTH_SHORT).show()
                Log.e("Gagal", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["user_id"] = Global.user_id.toString()
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun updateLeaderboard() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_leaderboard)
        recyclerView?.layoutManager = lm
        recyclerView?.adapter = LeaderboardAdapter(leaderboardList)
        // BUAT JALANIN ANIMATIONNYA, BUTUH INI KARENA VIEWPAGER KETIKA DILOAD PERTAMA KALI BAKAL NGELOAD/JALANIN ON RESUME di semua anak2nya
        recyclerView?.startLayoutAnimation()
    }

    override fun onResume() {
        super.onResume()
        getLeaderboard()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}