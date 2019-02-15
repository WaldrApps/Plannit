package waldrapps.plannit.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import waldrapps.plannit.Event
import waldrapps.plannit.fragments.TimetableFragment
import waldrapps.plannit.utils.Time.getCurrentDay

class TimetablePagerAdapter(private val contactID: String?, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val pageCount : Int = 7

    override fun getItem(position: Int): Fragment = TimetableFragment.new(position + 1, contactID)

    override fun getCount(): Int = pageCount

    override fun getPageTitle(position: Int): String {
        return getCurrentDay(position)
    }
}