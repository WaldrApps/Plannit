package waldrapps.plannit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timetable.*
import waldrapps.plannit.Event
import waldrapps.plannit.R
import waldrapps.plannit.adapters.EventAdapter
import waldrapps.plannit.utils.Constants
import waldrapps.plannit.viewmodels.EventViewModel

class TimetableFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val day = arguments?.getInt(Constants.ARG_DAY)
        val contactID = arguments?.getString(Constants.ARG_CONTACT_ID)

        if (day == null || contactID == null) return

        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = EventAdapter()
        recycler.adapter = adapter

        val eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        eventViewModel.getEventByContactIDAndDay(contactID, day).observe(this, android.arch.lifecycle.Observer { eventsList ->
            if (!eventsList!!.isEmpty()) {
                eventsList.sortBy { it.startTime }
                adapter.setEvents(eventsList)
            }
        })
    }

    companion object {
        fun new(day: Int, contactID: String?): TimetableFragment {
            val fragment = TimetableFragment()
            val arguments = Bundle()
            arguments.putInt(Constants.ARG_DAY, day)
            arguments.putString(Constants.ARG_CONTACT_ID, contactID)
            fragment.arguments = arguments
            return fragment
        }
    }
}