package id.ac.ubaya.dmd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_item_peringkat_emas.view.*

class LeaderboardAdapter (val list: MutableList<Users>): RecyclerView.Adapter<LeaderboardAdapter.GameViewHolder>() {

    class GameViewHolder(val v: View): RecyclerView.ViewHolder(v){
        // PENGATURAN VIEWNYA DENGAN CLASS USERS
        fun bindItem(isi: Users){
            val url = isi.url_img
            Picasso.get().load(url).into(itemView.profile_image)
            itemView.tv_leaderboard_name.text = "${isi.username}"
//            GET TOTAL LIKE
//            itemView.btn_display_like.text = "${isi.}"
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