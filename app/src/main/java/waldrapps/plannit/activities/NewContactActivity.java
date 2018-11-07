package waldrapps.plannit.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import waldrapps.plannit.R;
import waldrapps.plannit.fragments.NewContactFragment;

public class NewContactActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.CustomTheme1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        //Start new contact fragment
        if (savedInstanceState == null) {
            //Get uuid
            String uuid = getIntent().getStringExtra("uuid");
            //Pass uuid to fragment
            Bundle bundle = new Bundle();
            bundle.putString("uuid", uuid);
            NewContactFragment fragment = NewContactFragment.newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
