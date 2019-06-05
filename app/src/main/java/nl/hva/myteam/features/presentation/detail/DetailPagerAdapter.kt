package nl.hva.myteam.features.presentation.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import nl.hva.myteam.features.presentation.detail.levelup.LevelUpFragment
import nl.hva.myteam.features.presentation.detail.machine.MachineFragment

class DetailPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LevelUpFragment()
            1 -> MachineFragment()
            else -> Fragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "By level up"
            1 -> "By machine"
            else -> ""
        }
    }

}