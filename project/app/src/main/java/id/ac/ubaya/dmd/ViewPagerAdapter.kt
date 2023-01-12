package id.ac.ubaya.dmd

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (val activity: AppCompatActivity, val fragments:ArrayList<Fragment>) : FragmentStateAdapter(activity){
//    Ambil banyaknya fragment
    override fun getItemCount(): Int {
        return fragments.size
    }

//    define fragment sesuai dengan yang dipilih
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}