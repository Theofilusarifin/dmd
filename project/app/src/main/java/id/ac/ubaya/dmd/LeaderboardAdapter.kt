package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.layout_item_peringkat_default.view.*

class LeaderboardAdapter (val list: MutableList<Leaderboards>): RecyclerView.Adapter<LeaderboardAdapter.GameViewHolder>() {

    class GameViewHolder(val v: View): RecyclerView.ViewHolder(v){
        // PENGATURAN VIEWNYA DENGAN CLASS USERS
        @SuppressLint("SetTextI18n")
        fun bindItem(user: Leaderboards){
//            Get Image
            val url = user.url_img
            if (url != ""){
                Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(itemView.profile_image)
            }

            val firstName = user.first_name
            val lastName = user.last_name

//            Check privacy
            var i = 0
            if (user.privacy_setting == 1){
                var fullName = ""
                for (ch in firstName.iterator()) {
                    if (i < 3){
                        fullName += ch
                    }
                    else{
                        fullName += "*"
                    }
                    i++
                }
                fullName += " "
                for (ch in lastName.iterator()) {
                    if (i < 3){
                        fullName += ch
                    }
                    else{
                        fullName += "*"
                    }
                    i++
                }
                itemView.tv_leaderboard_name.text = fullName
            }
            else{
                itemView.tv_leaderboard_name.text = firstName + " " + lastName
            }

//            Set total like
            itemView.tv_total_likes.text = user.total_like.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardAdapter.GameViewHolder {
        // PENGATURAN TEMPLATE LAYOUT ITEM MANA YG DIGUNAKAN
        var v: View;
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == 1){
            v = inflater.inflate(R.layout.layout_item_peringkat_emas, parent, false)
        }else if(viewType == 2){
            v = inflater.inflate(R.layout.layout_item_peringkat_perak, parent, false)
        }else if(viewType == 3){
            v = inflater.inflate(R.layout.layout_item_peringkat_perunggu, parent, false)
        }else{
            v = inflater.inflate(R.layout.layout_item_peringkat_default, parent, false)
        }
        return GameViewHolder(v)
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.GameViewHolder, position: Int) {
        GameViewHolder(holder.itemView).bindItem(list.elementAt(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        // PENGATURAN KATEGORINYA
        if(position == 0){
            // return emas
            return 1
        }
        else if (position == 1){
            // return perak
            return 2
        }
        else if (position == 2){
            // return perunggu
            return 3
        }
        else {
            return 4
        }
    }
}