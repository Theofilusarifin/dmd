package id.ac.ubaya.dmd

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.layout_item_comment.view.*
import org.json.JSONObject

class CommentListAdapter(val listComment: ArrayList<Comments>) :
    RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder>() {
    class CommentListViewHolder(val v: View) : RecyclerView.ViewHolder(v)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.layout_item_comment, parent, false)
        return CommentListViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: CommentListViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
//        Check Button like
        //        Check button like
        if (listComment[position].liked) {
            holder.v.btn_like_comment.setImageResource(R.drawable.like_filled)
        }

//        Check Privacy
        var i = 0
        if (listComment[position].privacy_setting == 1){
            var fullName = ""
            for (ch in listComment[position].first_name.iterator()) {
                if (i < 3){
                    fullName += ch
                }
                else{
                    fullName += "*"
                }
                i++
            }
            fullName += " "
            for (ch in listComment[position].last_name.iterator()) {
                if (i < 3){
                    fullName += ch
                }
                else{
                    fullName += "*"
                }
                i++
            }
            holder.v.txtUsername.text = fullName
        }
        else{
            holder.v.txtUsername.text = listComment[position].first_name + " " + listComment[position].last_name
        }
        holder.v.txtContent.text = listComment[position].content
        holder.v.txtDate.text = listComment[position].created_at
        holder.v.txtCommentLikeNum.text = listComment[position].total_like.toString() + " Likes"

        //        Button Like
        holder.v.btn_like_comment.setOnClickListener {
//              Check wether the login user already like the meme or not
            if (!listComment[position].liked) {
                val queue = Volley.newRequestQueue(holder.v.context)
                val url = "https://ubaya.fun/native/160420108/api/add_like_comment.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
                            // Update Like
                            listComment[position].total_like++
                            // Update Boolean
                            listComment[position].liked = true
                            // Update Total Like Text and Icon
                            var newlikes = listComment[position].total_like
                            holder.v.txtCommentLikeNum.text = newlikes.toString() + " Likes"
                            holder.v.btn_like_comment.setImageResource(R.drawable.like_filled);
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
                        params["comment_id"] = listComment[position].id.toString()
                        return params
                    }
                }
                queue.add(stringRequest)
            } else {
                val queue = Volley.newRequestQueue(holder.v.context)
                val url = "https://ubaya.fun/native/160420108/api/remove_like_comment.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        val obj = JSONObject(it)
                        if (obj.getString("status") == "success") {
                            // Update Like
                            listComment[position].total_like--
                            // Update Boolean
                            listComment[position].liked = false
                            // Update Total Like Text and Icon
                            var newlikes = listComment[position].total_like
                            holder.v.txtCommentLikeNum.text = newlikes.toString() + " Likes"
                            holder.v.btn_like_comment.setImageResource(R.drawable.like);
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
                        params["comment_id"] = listComment[position].id.toString()
                        return params
                    }
                }
                queue.add(stringRequest)
            }
        }
    }

    override fun getItemCount(): Int = listComment.size

}