package id.ac.ubaya.dmd

data class Memes(val id:Int,
                 val user_id:Int,
                 val url_img:String,
                 val top_text:String,
                 val bottom_text:String,
                 var total_like:Int,
                 var liked:Boolean)
