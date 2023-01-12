package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meme_card.view.*
import org.json.JSONObject

class MemelistAdapter(private val context: Context, val listMemes: ArrayList<Memes>) :
    RecyclerView.Adapter<MemelistAdapter.MemelistViewHolder>() {
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
        Picasso.get().load(url).into(holder.v.iv_meme_detail)
        holder.v.tv_detail_top.text = listMemes[position].top_text
        holder.v.tv_detail_bottom.text = listMemes[position].bottom_text
        holder.v.tv_user_like.text = listMemes[position].total_like.toString() + " Likes"
//        Check button like
        if (listMemes[position].liked) {
            holder.v.btn_user_like.setImageResource(R.drawable.like_filled)
        }
        else{
            holder.v.btn_user_like.setImageResource(R.drawable.like)
        }


//        Button Like
        holder.v.btn_user_like.setOnClickListener {
//              Check wether the login user already like the meme or not
            if (!listMemes[position].liked) {
                val queue = Volley.newRequestQueue(holder.v.context)
//            IP Arifin
//                val url = "http://192.168.100.37/dmd/api/add_like.php"
                val url = "https://ubaya.fun/native/160420108/api/add_like.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
                            // Update Like
                            listMemes[position].total_like++
                            // Update Boolean
                            listMemes[position].liked = true
                            // Update Total Like Text and Icon
                            var newlikes = listMemes[position].total_like
                            holder.v.tv_user_like.text = newlikes.toString() + " Likes"
                            holder.v.btn_user_like.setImageResource(R.drawable.like_filled);
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
                val queue = Volley.newRequestQueue(holder.v.context)
//            IP Arifin
//                val url = "http://192.168.100.37/dmd/api/add_like.php"
                val url = "https://ubaya.fun/native/160420108/api/remove_like.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
                            // Update Like
                            listMemes[position].total_like--
                            // Update Boolean
                            listMemes[position].liked = false
                            // Update Total Like Text and Icon
                            var newlikes = listMemes[position].total_like
                            holder.v.tv_user_like.text = newlikes.toString() + " Likes"
                            holder.v.btn_user_like.setImageResource(R.drawable.like);
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
            }
        }

//        Button Report
        holder.v.btn_user_report.setOnClickListener {
//          Check wether the login user already like the meme or not
            val queue = Volley.newRequestQueue(holder.v.context)
//          IP Arifin
//            val url = "http://192.168.100.37/dmd/api/add_report.php"
            val url = "https://ubaya.fun/native/160420108/api/add_report.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    val obj = JSONObject(it)
                    if (obj.getString("status") == "success") {
//                        Show Msg
                        Toast.makeText(holder.v.context, obj.getString("msg"), Toast.LENGTH_LONG)
                            .show()
                        // Perbarui listMemesnya
                        // Hapus sesuai lokasi pada clicknya
                        listMemes.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listMemes.size)
                    } else {
                        Toast.makeText(holder.v.context, obj.getString("msg"), Toast.LENGTH_LONG)
                            .show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(holder.v.context, "Error Add Report", Toast.LENGTH_SHORT)
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
        }

//        Button See Detail
        holder.v.btn_user_detail.setOnClickListener{
            val memeId = listMemes[position].id
//            Create a new intent
            val intent = Intent(context, DetailMemeActivity::class.java)
//            Add extra to the intent using extras
            intent.putExtra("EXTRA_MEME_ID", memeId)
//            Start a new activity by using the crated intent
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listMemes.size
}