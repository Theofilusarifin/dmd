package id.ac.ubaya.dmd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*


class MainActivity : AppCompatActivity() {
    // Buat list fragment
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        var drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name, R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        supportActionBar?.title = ""

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.itemHome -> Toast.makeText(this, "Premium", Toast.LENGTH_SHORT).show()
                R.id.itemMyCreation -> Toast.makeText(this, "Chart", Toast.LENGTH_SHORT).show()
                R.id.itemLeaderbord -> Toast.makeText(this, "Playlist", Toast.LENGTH_SHORT).show()
                R.id.itemSettings -> Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        navView.fabMenuLogout.setOnClickListener {
            Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show()
        }

        // ViewPager Process
        // Tambahin fragment ke listnya
        fragments.add(HomeFragment())
        fragments.add(MyCreationFragment())
        fragments.add(LeaderboardFragment())
        fragments.add(SettingFragment())

        // Aktifin ViewPagernya
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
    }

    override fun onBackPressed() {
        Toast.makeText(this, "You cannot go back", Toast.LENGTH_SHORT).show()
    }
}