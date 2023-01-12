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

class MyCreationFragment : Fragment() {
    var myMemeList:ArrayList<Memes> = ArrayList()
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
        return inflater.inflate(R.layout.fragment_my_creation, container, false)
    }

    fun getMyMemelist(){
        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420108/api/get_created_memes.php"
        myMemeList = ArrayList()

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)

                if (obj.getString("status") == "success") {
                    val memes = obj.getJSONArray("memes")
                    for (i in 0 until memes.length()){
                        val memeObject = memes.getJSONObject(i)
                        val meme = Memes(
                            memeObject.getInt("id"),
                            memeObject.getInt("user_id"),
                            memeObject.getString("url_img"),
                            memeObject.getString("top_text"),
                            memeObject.getString("bottom_text"),
                            memeObject.getString("created_at"),
                            memeObject.getInt("total_like"),
                            memeObject.getInt("total_report"),
                            memeObject.getInt("total_comment"),
                            memeObject.getBoolean("liked")
                        )
                        myMemeList.add(meme)
                    }
//                    Update Meme
                    updateMemelist()
                } else {
                    Toast.makeText(activity, obj.getString("msg"), Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Error Get Memes", Toast.LENGTH_SHORT).show()
                Log.e("Gagal Get My Meme", it.toString())
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

    fun updateMemelist() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_usersmeme)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = UserCreationAdapter(this.requireContext(), myMemeList)
    }

    override fun onResume() {
        super.onResume()
        getMyMemelist()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCreationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}