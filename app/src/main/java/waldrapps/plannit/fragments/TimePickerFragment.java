package waldrapps.plannit.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import kotlin.Unit;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Button parentButton;
    static String tag = "TIME_PICKER";

    public static View newInstance(FragmentManager manager, View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setParentView((Button) view);
        timePickerFragment.show(manager, tag);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the picker
        final Calendar calendar = Calendar.getInstance();

        return new TimePickerDialog(
                getActivity(),
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity())
        );
    }

    public void setParentView(Button parentButton) {
        this.parentButton = parentButton;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        parentButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
    }
}
