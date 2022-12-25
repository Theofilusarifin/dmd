package id.ac.ubaya.dmd

data class Users(val id:Int, var username:String,
                 var first_name:String, var last_name:String = "",
                 var password:String, var registration_date:String, var url_img:String, var privacy:Boolean){
    //Template buat bikin timestamp di android
    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
    //String format = simpleDateFormat.format(new Date());
    //Log.d("MainActivity", "Current Timestamp: " + format);
}
