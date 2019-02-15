package waldrapps.plannit.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timetable_overview.view.*
import waldrapps.plannit.R
import waldrapps.plannit.utils.Constants
import waldrapps.plannit.viewmodels.EventViewModel

class OverviewFragment : Fragment() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_timetable_overview, container, false)

        //Set event data in schedule
        val eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        eventViewModel.getEventsByContactIDLive(arguments?.getString(Constants.ARG_CONTACT_ID)).observe(this, Observer { it ->
            view.schedule.setData(it!!)
        })
        return view
    }

    companion object {
        @JvmStatic fun newInstance(contactID: String): OverviewFragment {
            val fragment = OverviewFragment()
            val arguments = Bundle()
            arguments.putString(Constants.ARG_CONTACT_ID, contactID)
            fragment.arguments = arguments
            return fragment
        }
    }
}