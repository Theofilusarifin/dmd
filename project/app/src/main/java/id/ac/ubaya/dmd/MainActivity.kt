package id.ac.ubaya.dmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Buat list fragment
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager Process
        // Tambahin fragment ke listnya
        fragments.add(HomeFragment())
        fragments.add(MyCreationFragment())
        fragments.add(LeaderboardFragment())
        fragments.add(SettingFragment())

        // Aktifin ViewPagernya
        viewPager.adapter = ViewPagerAdapter(this, fragments)

        // Sinkronisasi Bottom Nav dengan ViewPager
        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                // Kalau page dari viewPager diselect update Bottom Nav
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })

        // Sinkronisasi ViewPager dengan Bottom Nav
        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemMyCreation -> 1
                R.id.itemLeaderbord -> 2
                R.id.itemSettings -> 3
                else -> 0 // defaultnya home
            }
            true
        }
    }
}