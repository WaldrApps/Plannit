package waldrapps.plannit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_new_contact.*
import kotlinx.android.synthetic.main.fragment_new_event.*
import kotlinx.android.synthetic.main.fragment_new_event.view.*
import waldrapps.plannit.Event
import waldrapps.plannit.R
import waldrapps.plannit.utils.Constants
import waldrapps.plannit.utils.Time.getParsedTimeInMinutes
import waldrapps.plannit.viewmodels.EventViewModel
import java.time.LocalTime
import java.util.*

class NewEventFragment : BaseFragment() {
    
    private var uuid: String? = null
    private var eventViewModel: EventViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_event, container, false)

        uuid = arguments!!.getString(getString(R.string.uuid))

        //Time pickers
        view.buttonStartTime.setOnClickListener(timePickerDialogListener())
        view.buttonEndTime.setOnClickListener(timePickerDialogListener())

        //Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
                Objects.requireNonNull<Context>(context),
                R.array.days_of_week_array,
                android.R.layout.simple_spinner_item
        )
        //Specify the layout to use when the list of choices appears (currently android default)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinner.adapter = adapter

        view.fab.setOnClickListener {
            //Add event to database and navigate back to new contact
            eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
            eventViewModel?.insert(
                    Event (
                        UUID.randomUUID().toString(),
                        uuid,
                        editTextName.text.toString(),
                        spinner.selectedItemPosition + 1,
                        editTextPlace.text.toString(),
                        getParsedTimeInMinutes(buttonStartTime.text.toString()),
                        getParsedTimeInMinutes(buttonEndTime.text.toString())
                    )
            )
            val fragmentManager = Objects.requireNonNull<FragmentActivity>(activity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = NewContactFragment.newInstance()
            //Pass uuid to fragment
            val bundle = Bundle()
            bundle.putString(getString(R.string.uuid), uuid)
            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    private fun timePickerDialogListener(): View.OnClickListener {
        return View.OnClickListener {v -> TimePickerFragment.newInstance(Objects.requireNonNull<FragmentActivity>(activity).supportFragmentManager, v)}
    }

    companion object {
        fun newInstance(): NewEventFragment {
            return NewEventFragment()
        }
    }
}