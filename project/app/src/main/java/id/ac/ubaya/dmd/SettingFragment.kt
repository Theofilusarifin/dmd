package id.ac.ubaya.dmd

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {
    var currentUser = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_setting, container, false)
        // Inflate the layout for this fragment

        // Siapin listener buat klo button fabnya diclick (Membuka activity baru)
        v.fabEditProfile.setOnClickListener {
            val parentActivity: Activity? = activity
            val intent = Intent(parentActivity, EditProfileActivity::class.java)
            requireActivity().startActivity(intent)
        }

//        Button Logout
        v.btnLogout.setOnClickListener {
            Global.user_id = 0
            Global.username = ""
            Global.firstName = ""
            Global.lastName = ""
            Global.password = ""
            Global.registrationDate = ""
            Global.urlImg = ""
            Global.privacySetting = 0

//            Reset Shared Preference
            val sharedFile = "id.ac.ubaya.dmd"
            val shared: SharedPreferences? =
                this.activity?.getSharedPreferences(sharedFile, Context.MODE_PRIVATE) ?: null
            val editor: SharedPreferences.Editor? = shared?.edit() ?: null
            editor?.putInt("id", 0)
            editor?.apply()

//            Back to Login Activity
            val parentActivity: Activity? = activity
            val intentLogin = Intent(parentActivity, LoginActivity::class.java)
            startActivity(intentLogin)
            activity?.finish()
        }
        return v
    }

    override fun onResume() {
        super.onResume()
//        Set login user
        txtProfileName.text = Global.firstName + " " + Global.lastName
        var dateDatabase = Global.registrationDate
        var tahun = dateDatabase.subSequence(0, 4)
        var bulan:CharSequence = dateDatabase.subSequence(5, 7)
        var namaBulan = StringMonth(bulan)
        txtRegistrationDate.text = "Active since $namaBulan $tahun"
        txtProfileUsername.text = "@" + Global.username

        //            Get Image
        val url = Global.urlImg
        if (url != ""){
            Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(imagePhotoProfile)
        }

        if (Global.privacySetting == 1){
            txtProfileTypeAccount.text = "Private Account"
        }
        else{
            txtProfileTypeAccount.text = "Public Account"
        }
    }

    fun StringMonth(bulan: CharSequence):String{
        var monthString: String = ""
        monthString = when(bulan){
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "January" // defaultnya home
        }
        return monthString
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}