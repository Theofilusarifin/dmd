package id.ac.ubaya.dmd

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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

        navView.setNavigationItemSelectedListener(this)

        supportActionBar?.title = ""

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

//        Set Default yang aktif adalah home
        navView.getMenu().getItem(0).setChecked(true)
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
}