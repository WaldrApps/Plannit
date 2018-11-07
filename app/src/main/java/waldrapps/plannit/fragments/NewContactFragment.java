package waldrapps.plannit.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.framgia.library.calendardayview.CalendarDayView;

import java.util.List;
import java.util.Objects;

import waldrapps.plannit.Contact;
import waldrapps.plannit.Event;
import waldrapps.plannit.activities.HomeScreenActivity;
import waldrapps.plannit.R;
import waldrapps.plannit.adapters.CalendarPagerAdapter;
import waldrapps.plannit.viewmodels.ContactViewModel;
import waldrapps.plannit.viewmodels.EventViewModel;

public class NewContactFragment extends BaseFragment {

    private ContactViewModel contactViewModel;
    private EventViewModel eventViewModel;
    CalendarDayView schedule;
    private String uuid;

    public static NewContactFragment newInstance() {
        return new NewContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initialize the view models
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        //Get uuid
        uuid = getArguments().getString("uuid");


        //Set up the ViewPager with the sections adapter
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        CalendarPagerAdapter calendarPagerAdapter = new CalendarPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), 7);
        viewPager.setAdapter(calendarPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        schedule = view.findViewById(R.id.schedule);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start fragment for event creation form
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                NewEventFragment fragment = NewEventFragment.newInstance();
                //Pass uuid to fragment
                Bundle bundle = new Bundle();
                bundle.putString("uuid", uuid);
                fragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add contact to database and navigate back to main screen
                contactViewModel.insert(new Contact(uuid, "name", "s", 0));
                startActivity(new Intent(getContext(), HomeScreenActivity.class));
            }
        });

        eventViewModel.getEventByContactAndDay(uuid, viewPager.getCurrentItem()).observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(final List<Event> events) {
                //Update the cached copy of the events in the schedule
                if(!events.isEmpty()) {
                    schedule.setEvents(events);
                }
                Log.d("TEST", events.toString());
            }
        });

        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(final List<Event> events) {
                Log.d("TEST", events.toString());
            }
        });

        return view;
    }
}
