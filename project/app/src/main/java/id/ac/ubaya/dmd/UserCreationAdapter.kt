package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_meme_card.view.*

class UserCreationAdapter(val listMemes:ArrayList<Memes>)
    : RecyclerView.Adapter<UserCreationAdapter.UserCreationViewHolder>() {
    class UserCreationViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCreationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.user_meme_card, parent,false)
        return UserCreationViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserCreationViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //        Get Memes data
        val url = listMemes[position].url_img
        Picasso.get().load(url).into(holder.v.iv_user_meme_preview)
        holder.v.tv_user_preview_top.text = listMemes[position].top_text
        holder.v.tv_user_preview_bottom.text = listMemes[position].bottom_text
        holder.v.tv_user_like.text = listMemes[position].total_like.toString() + " Likes"
        holder.v.tv_user_report.text = listMemes[position].total_report.toString() + " Reports"
        holder.v.tv_user_comment.text = listMemes[position].total_comment.toString() + " Comments"

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

//        holder.v.btn_detail.setOnClickListener{
//            val q = Volley.newRequestQueue(holder.v.context)
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