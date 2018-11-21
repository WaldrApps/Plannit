package waldrapps.plannit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import waldrapps.plannit.Contact;
import waldrapps.plannit.R;
import waldrapps.plannit.activities.HomeScreenActivity;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;

        ContactViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.contact_name);
            icon = view.findViewById(R.id.person_icon);
        }
    }

    private final LayoutInflater inflater;
    //Cached copy of contacts
    private List<Contact> contacts;

    public ContactAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_contact, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Recycler",
                        Toast.LENGTH_LONG).show();
            }
        });
        return new ContactViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {
        if(contacts != null) {
            Contact current = contacts.get(position);
            //Find current date and time
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            c.set(Calendar.HOUR_OF_DAY, 20);
            c.set(Calendar.MINUTE, 21);
            int day = c.get(Calendar.DAY_OF_WEEK);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            int pos = -1;
            if((day >= Calendar.MONDAY) && (day <= Calendar.FRIDAY))
            {
                if(hour >= 8 && hour <= 21)
                {
                    pos = (28 * (day - 2)) + (2 * (hour - 8)) + (min / 30);
                }
            }

            //Set icon based on flag
            if (current.getFlag().equals("g")) {
                holder.icon.setImageResource(R.drawable.ic_people);
            }
            else {
                holder.icon.setImageResource(R.drawable.ic_person_white_24dp);
            }

            //Set name
            holder.name.setText(current.getName());

        } else {
            //Covers the case of data not being ready yet
            holder.name.setText(R.string.no_name);
        }
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getItem(int pos) {
        return contacts.get(pos);
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        else {
            return 0;
        }
    }
}
