package id.ac.ubaya.dmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.fragment_setting.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // Buat list fragment
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

//        Untuk nampilin menu navigation drawer yang kiri atas
        setSupportActionBar(toolbar)
//        Set supaya jadi hamburger
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        Set title supaya kosong (masalah display kecantikan saja)
        supportActionBar?.title = ""
//        Set drawer_layout pada toolbar
        var drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name, R.string.app_name)
//        Kalau drawer ada perubahan
        drawerToggle.isDrawerIndicatorEnabled = true
//        Sinkonisasi
        drawerToggle.syncState()
        //        Set navigation menu yang ada di bawah
        navView.setNavigationItemSelectedListener(this)

        // ViewPager Process
        // Tambahin fragment ke listnya supaya viewpager bisa berjalan
        fragments.add(HomeFragment())
        fragments.add(MyCreationFragment())
        fragments.add(LeaderboardFragment())
        fragments.add(SettingFragment())

        // Aktifin ViewPagernya dengan fragment yang telah ditambahkan
        rvListOfComment.adapter = ViewPagerAdapter(this, fragments)

        // Sinkronisasi Bottom Nav dengan ViewPager
        rvListOfComment.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                // Kalau page dari viewPager diselect update Bottom Nav
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })

        // Sinkronisasi ViewPager dengan Bottom Nav
        bottomNav.setOnItemSelectedListener {
            rvListOfComment.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemMyCreation -> 1
                R.id.itemLeaderbord -> 2
                R.id.itemSettings -> 3
                else -> 0 // defaultnya home
            }
            true
        }
//        Set Default yang aktif adalah home
        navView.getMenu().getItem(0).setChecked(true)

        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        drawer.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
                // Update active drawernya
                navView.getMenu().getItem(rvListOfComment.currentItem).setChecked(true)

                navView.txtMenuName.text = Global.firstName + " " + Global.lastName
                navView.txtMenuUsername.text = "@" + Global.username
                //            Get Image
                val url = Global.urlImg
                if (url != ""){
                    Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(navView.imgProfile)
                }
//                Logout
                navView.fabMenuLogout.setOnClickListener {
                    logout()
                }
            }
        })


    }

    override fun onBackPressed() {
        Toast.makeText(this, "You cannot go back", Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.itemHome) {
            rvListOfComment.currentItem = 0
        }
        else if (id == R.id.itemMyCreation) {
            rvListOfComment.currentItem = 1
        }
        else if (id == R.id.itemLeaderbord) {
            rvListOfComment.currentItem = 2
        }
        else if (id == R.id.itemSettings) {
            rvListOfComment.currentItem = 3
        }
        navView.getMenu().getItem(rvListOfComment.currentItem).setChecked(true)
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout(){
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
            val shared: SharedPreferences = this.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = shared.edit()
            editor.putInt("id", 0)
            editor.apply()

//            Back to Login Activity
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
            this.finish()
    }
}