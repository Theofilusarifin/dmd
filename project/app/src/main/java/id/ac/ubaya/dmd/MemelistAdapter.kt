package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meme_card.view.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MemelistAdapter(val listMemes:ArrayList<Memes>)
    : RecyclerView.Adapter<MemelistAdapter.PlaylistViewHolder>() {
    class PlaylistViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.meme_card, parent,false)
        return PlaylistViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val url = listMemes[position].url_img
        Picasso.get().load(url).into(holder.v.iv_meme)
        holder.v.tv_meme_top_text.text = listMemes[position].top_text
        holder.v.tv_meme_bottom_text.text = listMemes[position].bottom_text
        holder.v.tv_like.text = listMemes[position].num_likes.toString() + " Likes"

//        TEMPLATE BUAT BTN LIKE DAN BTN DETAIL
//        holder.v.btn_like.setOnClickListener{
//            val q = Volley.newRequestQueue(holder.v.context)
//            val url = "http://192.168.43.237/mobile_db/set_likes.php"
////            val url = "http://192.168.18.109/mobile_db/set_likes.php"
//            val stringRequest = object : StringRequest(Request.Method.POST, url,
//                Response.Listener {
//                    Log.d("cekparams", it)
//                    listMemes[position].num_likes++
////                    notifyItemChanged(position)
//                    var newlikes = listMemes[position].num_likes
//                    holder.v.btn_like.text = "$newlikes LIKES" },
//                Response.ErrorListener {
//                    Log.d("cekparams", it.message.toString())
//                }
//            )
//            {
//                // anonymous object body
//                override fun getParams(): MutableMap<String, String> {
//                    val params = HashMap<String, String>()
//                    params["id"] = listMemes[position].id.toString()
//                    return params
//                }
//
//                //short version
////                override fun getParams() = hashMapOf("id" to playlists[position].id.toString())
//            }
//            // Perlu create volley baru
//            q.add(stringRequest)
//        }

    }

    override fun getItemCount(): Int = listMemes.size

}