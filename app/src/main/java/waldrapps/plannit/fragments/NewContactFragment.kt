package waldrapps.plannit.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.activity_new_contact.*
import kotlinx.android.synthetic.main.fragment_new_contact.view.*
import waldrapps.plannit.Contact
import waldrapps.plannit.R
import waldrapps.plannit.activities.HomeActivity
import waldrapps.plannit.adapters.TimetablePagerAdapter
import waldrapps.plannit.utils.Constants.ARG_COLOR
import waldrapps.plannit.viewmodels.ContactViewModel
import java.util.*


class NewContactFragment : BaseFragment() {

    private var contactViewModel: ContactViewModel? = null
    private var uuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_new_contact, container, false)

        uuid = arguments!!.getString(getString(R.string.uuid))

        view.view_pager.adapter = TimetablePagerAdapter(uuid, activity!!.supportFragmentManager)
        view.tabs.setupWithViewPager(view.view_pager)

        //Set page to current day
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        view.view_pager.currentItem = if (today in 0..6) today else 0

        view.button.setOnClickListener {
            //Start fragment for event creation form
            val fragmentManager = activity!!.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = NewEventFragment.newInstance()
            //Pass uuid to fragment
            val bundle = Bundle()
            bundle.putString(getString(R.string.uuid), uuid)
            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        view.fab.setOnClickListener {
            //Add contact to database and navigate back to main screen
            contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
            contactViewModel!!.insert(Contact(uuid, activity!!.toolbar.title.toString(), getString(R.string.single_flag), arguments!!.getString(ARG_COLOR)))
            startActivity(Intent(context, HomeActivity::class.java))
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

    companion object {
        @JvmStatic fun newInstance(): NewContactFragment {
            return NewContactFragment()
        }
    }
}
