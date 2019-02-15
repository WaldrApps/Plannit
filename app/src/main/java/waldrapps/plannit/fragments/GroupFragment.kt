package waldrapps.plannit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.fragment_group.view.*
import waldrapps.plannit.Contact
import waldrapps.plannit.R
import waldrapps.plannit.activities.HomeActivity
import waldrapps.plannit.adapters.ContactAdapter
import waldrapps.plannit.utils.Constants
import waldrapps.plannit.viewmodels.ContactViewModel
import waldrapps.plannit.viewmodels.EventViewModel
import java.util.*

/**
 * Allows a user to select two or more schedules to merge. The combined schedule will show the free
 * time between the two previous schedules
 */
class GroupFragment : BaseFragment() {

    private var selectedContacts : ArrayList<Contact> = arrayListOf()
    private var uuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_group, container, false)

        uuid = arguments!!.getString(getString(R.string.uuid))

        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        val adapter = ContactAdapter(activity!!, {}, this::checkBoxClicked)

        val contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, android.arch.lifecycle.Observer { it ->
            //Updated the cached copy of contacts in the adapter
            if (it != null) {
                adapter.updateContacts(it)
            }
        })
        view.recycler_view.adapter = adapter

        view.fab.setOnClickListener { _ ->
            //Make a new contact for the group
            contactViewModel.insert(Contact(uuid, activity!!.toolbar.title.toString(), getString(R.string.group_flag), arguments!!.getString(Constants.ARG_COLOR)))
            val eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
            selectedContacts.forEach {contact ->
                //Copy all events to new contact
                eventViewModel.getEventsByContactID(contact.id) { events ->
                    if(!events!!.isEmpty()) {
                        events.forEach { event ->
                            //Replace linked ID with ID of group contact
                            event.id = UUID.randomUUID().toString()
                            event.contactId = uuid
                            eventViewModel.insert(event)
                        }
                    }
                }
            }
            startActivity(Intent(activity, HomeActivity::class.java))
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.more_options -> {
                val fragmentManager = activity!!.supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                val fragment = OptionsFragment.newInstance()
                //Pass uuid to fragment
                val bundle = Bundle()
                bundle.putString(getString(R.string.uuid), uuid)
                fragment.arguments = bundle
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkBoxClicked(contact: Contact, clicked: Boolean) {
        //Toggle checkbox and add or remove contact from list of checked contacts
        if(clicked) {
            selectedContacts.add(contact)
        } else {
            selectedContacts.remove(contact)
        }
    }

    companion object {
        fun newInstance(): GroupFragment {
            return GroupFragment()
        }
    }
}