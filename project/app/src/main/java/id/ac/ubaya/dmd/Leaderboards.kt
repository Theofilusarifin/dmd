package id.ac.ubaya.dmd

data class Leaderboards(val id:Int,
                        var first_name:String,
                        var last_name:String = "",
                        var url_img:String,
                        var privacy_setting:Int,
                        var total_like:Int)