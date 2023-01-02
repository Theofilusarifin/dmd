package id.ac.ubaya.dmd

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_meme.*
import org.json.JSONObject

class DetailMemeActivity : AppCompatActivity() {
    //  Declare ArrayList of Comments
    var commentList:ArrayList<Comments> = ArrayList()
    var meme_id = 0
    private lateinit var adapter: CommentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)

        // Receive Intent Data
        meme_id = intent.getIntExtra("EXTRA_MEME_ID", 0)

        Log.e("meme id", meme_id.toString())
//        Get Comment Data First
        if (meme_id != 0){
            getMemeDetail()
            getComment()
        }

//        Button Back
        btnMemeDetailBack.setOnClickListener{
            finish()
        }

        btnAddComment.setOnClickListener{
            val queue = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160420108/api/add_comment.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    val obj = JSONObject(it)
                    if (obj.getString("status") == "success") {
                        txtComment.setText("")
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error Get Memes", Toast.LENGTH_SHORT).show()
                    Log.e("Gagal Add comment", it.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["user_id"] = Global.user_id.toString()
                    params["meme_id"] = meme_id.toString()
                    params["content"] = txtComment.text.toString()
                    return params
                }
            }
            queue.add(stringRequest)
        }
    }

    fun getMemeDetail(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160420108/api/get_meme_detail.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)
//                Update meme detail
                if (obj.getString("status") == "success") {
                    val meme = obj.getJSONObject("meme")
                    val url = meme.getString("url_img")
                    Picasso.get().load(url).into(iv_meme_detail)
                    tv_detail_top.text = meme.getString("top_text")
                    tv_detail_bottom.text = meme.getString("bottom_text")

                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error Get Memes", Toast.LENGTH_SHORT).show()
                Log.e("Gagal get meme detail", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["meme_id"] = meme_id.toString()
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun getComment(){
        commentList = ArrayList()

        val queue = Volley.newRequestQueue(this)
//        IP Arifin
        val url = "https://ubaya.fun/native/160420108/api/get_comment.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)

                if (obj.getString("status") == "success") {
//                    Get Comments
                    val comments = obj.getJSONArray("comments")
                    for (i in 0 until comments.length()){
                        val commentObject = comments.getJSONObject(i)
                        val comment = Comments(
                            commentObject.getInt("id"),
                            commentObject.getInt("user_id"),
                            commentObject.getString("first_name"),
                            commentObject.getString("last_name"),
                            commentObject.getInt("privacy_setting"),
                            commentObject.getInt("meme_id"),
                            commentObject.getString("content"),
                            commentObject.getString("created_at"),
                            commentObject.getInt("total_like"),
                            commentObject.getBoolean("liked")
                        )
                        commentList.add(comment)
                    }
                    Log.e("Comment list", commentList.toString())
                    UpgradeListComment()
                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error Get Memes", Toast.LENGTH_SHORT).show()
                Log.e("Gagal Get Comment", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["meme_id"] = meme_id.toString()
                params["user_id"] = Global.user_id.toString()
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun UpgradeListComment(){
        val lm: LinearLayoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_listofcomments)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        adapter = CommentListAdapter(commentList)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        UpgradeListComment()
    }
}

