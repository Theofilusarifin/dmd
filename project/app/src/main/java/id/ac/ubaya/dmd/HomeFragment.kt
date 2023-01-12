package id.ac.ubaya.dmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject

class HomeFragment : Fragment() {
//  Declare ArrayList of Memes
    var memelist:ArrayList<Memes> = ArrayList()
    var sortBySelected = "Newest"

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

        //        Set sort by location
        val adapter = ArrayAdapter(this.requireActivity(), R.layout.myspinner_layout, Global.sortBy)
        adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
        v.spinSort.adapter = adapter

        //        Spinner sort
        v.spinSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sortBySelected = Global.sortBy[p2].name
                getMemelist()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                sortBySelected = Global.sortBy[0].name
                getMemelist()
            }
        }

        // Siapin listener buat klo button fabnya diclick (Membuka activity baru)
        v.fab_addmeme.setOnClickListener {
            val parentActivity: Activity? = activity
            val intent = Intent(parentActivity, AddMemeActivity::class.java)
            requireActivity().startActivity(intent)
        }
        return v


    }

    fun getMemelist(){
        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420108/api/get_memes.php"
        memelist = ArrayList()

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
                        memelist.add(meme)
                    }
//                    Update Meme
                    updateMemelist()
                } else {
                    Toast.makeText(activity, obj.getString("msg"), Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Error Get Memes", Toast.LENGTH_SHORT).show()
                Log.e("Gagal", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["user_id"] = Global.user_id.toString()
                params["sort_by"] = sortBySelected
                return params
            }
        }
        queue.add(stringRequest)
    }

    public fun updateMemelist() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_listofcomments)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = MemelistAdapter(this.requireContext(), memelist)
    }

    override fun onResume() {
        super.onResume()
        getMemelist()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}