package id.ac.ubaya.dmd

object Global {
    var user_id:Int = 0
    var username:String = ""
    var firstName:String = ""
    var lastName:String = " "
    var password:String = ""
    var registrationDate:String = ""
    var urlImg:String = ""
    var privacySetting:Int = 0

    val sortBy:Array<SortBy> = arrayOf(
        SortBy(1,"Newest"),
        SortBy(2,"On Trending"),
        SortBy(3,"Most popular")
    )
}