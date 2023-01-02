package id.ac.ubaya.dmd

data class Comments(val id:Int,
                    val user_id:Int,
                    val first_name:String,
                    val last_name:String,
                    val privacy_setting:Int,
                    val meme_id:Int,
                    val content:String,
                    val created_at:String)
