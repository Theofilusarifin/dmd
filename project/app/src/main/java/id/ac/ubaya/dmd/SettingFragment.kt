package id.ac.ubaya.dmd

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
        txtProfileUsername.text = "@" + Global.username
        if (Global.privacySetting == 1){
            txtProfileTypeAccount.text = "Private Account"
        }
        else{
            txtProfileTypeAccount.text = "Public Account"
        }
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