package waldrapps.plannit.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import waldrapps.plannit.Event;
import waldrapps.plannit.R;
import waldrapps.plannit.viewmodels.EventViewModel;

public class NewEventFragment extends Fragment {

    private String uuid;
    private EventViewModel eventViewModel;

    public static NewEventFragment newInstance() {
        return new NewEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initialize the event view model
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        //Time pickers
        Button startTime = view.findViewById(R.id.buttonStartTime);
        startTime.setOnClickListener(timePickerDialogListener());
        Button endTime = view.findViewById(R.id.buttonEndTime);
        endTime.setOnClickListener(timePickerDialogListener());

        //Spinner
        final Spinner spinner = view.findViewById(R.id.spinner);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                Objects.requireNonNull(getContext()),
                R.array.days_of_week_array,
                android.R.layout.simple_spinner_item
        );
        //Specify the layout to use when the list of choices appears (currently android default)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        uuid = getArguments().getString("uuid");

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                //Add event to database and navigate back to new contact
                //TODO: Insert actual data
                Event event = new Event(
                        UUID.randomUUID().toString(),
                        uuid,
                        "name",
                        0,
                        spinner.getSelectedItemPosition(),
                        "place",
                        c,
                        c
                );
                eventViewModel.insert(event);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                NewContactFragment fragment = NewContactFragment.newInstance();
                //Pass uuid to fragment
                Bundle bundle = new Bundle();
                bundle.putString("uuid", uuid);
                fragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }


    public View.OnClickListener timePickerDialogListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "timePicker");
            }
        };
    }
}
