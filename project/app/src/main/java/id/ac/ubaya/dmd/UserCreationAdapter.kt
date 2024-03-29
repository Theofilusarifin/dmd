package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import kotlinx.android.synthetic.main.user_meme_card.view.*
import kotlinx.android.synthetic.main.user_meme_card.view.btn_user_detail
import kotlinx.android.synthetic.main.user_meme_card.view.btn_user_like
import kotlinx.android.synthetic.main.user_meme_card.view.iv_meme_detail
import kotlinx.android.synthetic.main.user_meme_card.view.tv_detail_bottom
import kotlinx.android.synthetic.main.user_meme_card.view.tv_detail_top
import kotlinx.android.synthetic.main.user_meme_card.view.tv_user_comment
import kotlinx.android.synthetic.main.user_meme_card.view.tv_user_like
import kotlinx.android.synthetic.main.user_meme_card.view.tv_user_report
import org.json.JSONObject

class UserCreationAdapter(private val context: Context, val listMemes:ArrayList<Memes>)
    : RecyclerView.Adapter<UserCreationAdapter.UserCreationViewHolder>() {
    class UserCreationViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCreationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.user_meme_card, parent,false)
        return UserCreationViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserCreationViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (listMemes[position].total_report >= 3){
            holder.v.userCreationConstraint.setBackgroundColor(Color.parseColor("#C6C7C9"))
        }
        else{
            holder.v.userCreationConstraint.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        //        Get Memes data
        val url = listMemes[position].url_img
        Picasso.get().load(url).into(holder.v.iv_meme_detail)
        holder.v.tv_user_date.text = listMemes[position].created_at
        holder.v.tv_detail_top.text = listMemes[position].top_text
        holder.v.tv_detail_bottom.text = listMemes[position].bottom_text
        holder.v.tv_user_like.text = listMemes[position].total_like.toString() + " Likes"
        holder.v.tv_user_report.text = listMemes[position].total_report.toString() + " Reports"
        holder.v.tv_user_comment.text = listMemes[position].total_comment.toString() + " Comments"

        holder.v.btn_user_detail.setOnClickListener {
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