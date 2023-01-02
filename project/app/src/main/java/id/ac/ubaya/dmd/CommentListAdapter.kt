package id.ac.ubaya.dmd

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_comment.view.*

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
//        Get Memes data
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
    }

    override fun getItemCount(): Int = listComment.size

}