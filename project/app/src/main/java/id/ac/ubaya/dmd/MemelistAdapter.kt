package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meme_card.view.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class MemelistAdapter(val listMemes:ArrayList<Memes>)
    : RecyclerView.Adapter<MemelistAdapter.MemelistViewHolder>() {
    class MemelistViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemelistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.meme_card, parent, false)
        return MemelistViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: MemelistViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
//        Get Memes data
        val url = listMemes[position].url_img
        Picasso.get().load(url).into(holder.v.iv_meme)
        holder.v.tv_meme_top_text.text = listMemes[position].top_text
        holder.v.tv_meme_bottom_text.text = listMemes[position].bottom_text
        holder.v.tv_like.text = listMemes[position].total_like.toString() + " Likes"
//        Check button like
        if (listMemes[position].liked) {
            holder.v.btn_like.setImageResource(R.drawable.like_filled);
        }

//        Button Like
        holder.v.btn_like.setOnClickListener {
//              Check wether the login user already like the meme or not
            if (!listMemes[position].liked) {
                val queue = Volley.newRequestQueue(holder.v.context)
//            IP Arifin
                val url = "http://192.168.100.37/dmd/api/add_like.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
//                    Update Like Count and Icon
                            listMemes[position].total_like++
                            var newlikes = listMemes[position].total_like
                            holder.v.tv_like.text = newlikes.toString() + " Likes"
                            holder.v.btn_like.setImageResource(R.drawable.like_filled);
//                        Show Msg
                            Toast.makeText(
                                holder.v.context,
                                obj.getString("msg"),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                holder.v.context,
                                obj.getString("msg"),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(holder.v.context, "Error Add Like", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("Gagal", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["user_id"] = Global.user_id.toString()
                        params["meme_id"] = listMemes[position].id.toString()
                        return params
                    }
                }
                queue.add(stringRequest)
            } else {
                Toast.makeText(holder.v.context, "You already like this meme!", Toast.LENGTH_SHORT)
            }
        }

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
//
//    }

    }

    override fun getItemCount(): Int = listMemes.size
}