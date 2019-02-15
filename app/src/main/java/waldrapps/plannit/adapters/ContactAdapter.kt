package waldrapps.plannit.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.item_contact.view.*
import waldrapps.plannit.Contact
import waldrapps.plannit.R
import waldrapps.plannit.activities.GroupActivity
import waldrapps.plannit.fragments.GroupFragment
import waldrapps.plannit.utils.getLayoutInflater

class ContactAdapter(
        val context : Context,
        private val clickListener: (contact: Contact) -> Unit,
        private val checkBoxListener : (contact: Contact, b: Boolean) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var contactsList: List<Contact>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.getLayoutInflater().inflate(R.layout.item_contact, parent, false)
        if(context is GroupActivity) {
            view.checkbox.visibility = View.VISIBLE
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, contactsList!![position], clickListener, checkBoxListener)
    }

    fun updateContacts(contacts: List<Contact>) {
        contactsList = contacts
        notifyDataSetChanged()
    }

    fun getItem(pos: Int): Contact {
        return contactsList!![pos]
    }

    override fun getItemCount(): Int {
        return if (contactsList != null) {
            contactsList!!.size
        } else {
            0
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(context: Context,
                contact: Contact,
                clickListener: (contact: Contact) -> Unit,
                checkBoxListener: (contact: Contact, b: Boolean) -> Unit?
        ) {
            view.card.setCardBackgroundColor(Color.parseColor(contact.color))
            view.contact_name.text = contact.name ?: "?"
            if(contact.flag == context.resources.getString(R.string.group_flag)) {
                view.person_icon.setImageResource(R.drawable.ic_people)
            } else {
                view.person_icon.setImageResource(R.drawable.ic_person_white_24dp)
            }
            view.setOnClickListener { clickListener(contact) }
            view.checkbox.setOnCheckedChangeListener { _: CompoundButton, b: Boolean -> checkBoxListener(contact, b)}
        }
    }
}