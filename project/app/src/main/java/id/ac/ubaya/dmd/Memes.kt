package id.ac.ubaya.dmd

data class Memes(val id:Int, val top_text:String,
                 val bottom_text:String,
                 val url_img:String,
                 var num_likes:Int, var num_reports:Int, var num_comments:Int, var creation_date:String)
