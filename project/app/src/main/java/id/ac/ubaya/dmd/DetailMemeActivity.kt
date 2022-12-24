package id.ac.ubaya.dmd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)
        // RECIEVE INTENT DATA
        val id = intent.getIntExtra("id", 0)
        val num_likes = intent.getIntExtra("num_likes", 0)
        val num_reports = intent.getIntExtra("num_reports", 0)
        val url_img = intent.getStringExtra("url_img")
        val top_text = intent.getStringExtra("top_text")
        val bottom_text = intent.getStringExtra("bottom_text")

//        VOLLEY TO GET MEME LIST OF COMMENT
//        val q = Volley.newRequestQueue(this)
//            val url = "http://192.168.43.237/mobile_db/set_likes.php"
////            val url = "http://192.168.18.109/mobile_db/set_likes.php"
//            val stringRequest = object : StringRequest(Request.Method.POST, url,
//                Response.Listener {
//                    Log.d("cekparams", it)
//                    IF TRUE INTENT TO NEW ACTIVITY
//                Response.ErrorListener {
//                    Log.d("cekparams", it.message.toString())
//                }
//            )
//            {
//                // anonymous object body
//                override fun getParams(): MutableMap<String, String> {
//                    val params = HashMap<String, String>()
//                    params["id"] = id
//                    return params
//                }
//
//                //short version
////                override fun getParams() = hashMapOf("id" to playlists[position].id.toString())
//            }
//            // Perlu create volley baru
//            q.add(stringRequest)

//        ADAPTER UNTUK KONTROL COMMENT

//        VOLLEY UNTUK ADD COMMENT + REFRESH
    }
}