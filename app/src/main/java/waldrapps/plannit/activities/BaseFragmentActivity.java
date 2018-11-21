package waldrapps.plannit.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import waldrapps.plannit.R;

public abstract class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = preferences.getString("themes_list", "-1"); // Forced default if error

        if(themePref.equals("0"))
        {
            setTheme(R.style.CustomTheme1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
    }
}
